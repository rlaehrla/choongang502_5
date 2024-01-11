package org.choongang.admin.product.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.menus.AdminMenu;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.MenuDetail;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertException;
import org.choongang.product.constants.MainCategory;
import org.choongang.product.entities.Category;
import org.choongang.product.service.CategoryInfoService;
import org.choongang.product.service.CategorySaveService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller("adminProductController")
@RequiredArgsConstructor
@RequestMapping("/admin/product")
public class ProductController implements ExceptionProcessor {

    private final CategoryValidator categoryValidator;
    private final CategorySaveService categorySaveService;
    private final CategoryInfoService categoryInfoService;
    @ModelAttribute("menuCode")
    public String getMenuCode(){
        return "product";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus(){
        return AdminMenu.getMenus("product");
    }

    /**
     * 상품 목록
     * @param model
     * @return
     */
    @GetMapping
    public String list(Model model){

        commonProcess("list", model);


        return "admin/product/list";
    }

    /**
     * 상품 등록
     * @param model
     * @return
     */

    @GetMapping("/add")
    public String add(Model model){

        commonProcess("add", model);

        return "admin/product/add";
    }

    /**
     * 상품 등록, 수정처리
     * @param model
     * @return
     */
    @PostMapping("/add")
    public String save(Model model){

        return "redirect:/admin/product";

    }


    @GetMapping("/category")
    public String category(@ModelAttribute RequestCategory form, Model model){
        commonProcess("category", model);

        List<Category> items = categoryInfoService.getList(true);

        model.addAttribute("items", items);


        return "admin/product/category";

    }

    /**
     * 상품분류 추가, 수정
     * @return
     */

    @PostMapping("/category")
    public String categoryPs(@Valid RequestCategory form, Errors errors, Model model){
        commonProcess("category", model);

        categoryValidator.validate(form, errors);

        if(errors.hasErrors()){
            List<String> messages = errors.getFieldErrors().stream()
                    .map(e -> e.getCodes())
                    .map(s -> Utils.getMessage(s[0]))
                    .toList();

            throw new AlertException(messages.get(0), HttpStatus.BAD_REQUEST);
        }

        categorySaveService.save(form);

        //분류 추가가 완료되면 부모창 새로고침
        model.addAttribute("script", "parent.location.reload()");

        return "common/_execute_script";
    }

    @PatchMapping("/category")
    public String categoryEdit(@RequestParam("chk") List<Integer> chks, Model model){
        commonProcess("category", model);

        // 수정 완료 -> 새로고침
        model.addAttribute("script", "parent.location.reload()");

        return "common/_execute_script";
    }



    @DeleteMapping("/category")
    public String categoryDelete(@RequestParam("chk") List<Integer> chks, Model model){

        commonProcess("category", model);

        // 삭제 완료 -> 새로고침
        model.addAttribute("script", "parent.location.reload()");
        return "common/_execute_script";
    }


    /**
     * 공통처리부분
     *
     * @param mode
     * @param model
     */
    private void commonProcess(String mode, Model model){

        mode = Objects.requireNonNullElse(mode, "list");
        String pageTitle = "상품목록";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if(mode.equals("add") || mode.equals("edit")){
            pageTitle = mode.equals("edit")? "상품수정" : "상품등록";
            addCommonScript.add("ckeditor5/ckeditor");
            addCommonScript.add("fileManager");
            addScript.add("product/form");


        } else if (mode.equals("category")) {
            pageTitle = "상품분류";
            model.addAttribute("mainCategory", MainCategory.getList());
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
        model.addAttribute("subMenuCode", mode);
    }
}

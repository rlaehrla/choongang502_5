package org.choongang.member.controllers;


import lombok.RequiredArgsConstructor;
import org.choongang.admin.product.controllers.ProductSearch;
import org.choongang.board.controllers.BoardDataSearch;
import org.choongang.board.entities.BoardData;
import org.choongang.board.service.SaveBoardDataService;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Member;
import org.choongang.member.entities.Point;
import org.choongang.member.service.PointInfoService;
import org.choongang.order.entities.OrderInfo;
import org.choongang.order.entities.OrderItem;
import org.choongang.order.repositories.OrderInfoRepository;
import org.choongang.order.service.OrderInfoService;
import org.choongang.product.entities.Product;
import org.choongang.product.entities.ProductWish;
import org.choongang.product.service.ProductInfoService;
import org.choongang.product.service.ProductWishService;
import org.choongang.recipe.controllers.RecipeDataSearch;
import org.choongang.recipe.entities.Recipe;
import org.choongang.recipe.entities.RecipeWish;
import org.choongang.recipe.services.RecipeInfoService;
import org.choongang.recipe.services.RecipeWishNotFoundException;
import org.choongang.recipe.services.RecipeWishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController implements ExceptionProcessor {
    private final Utils utils;
    private final FileInfoService fileInfoService;
    private final OrderInfoService orderInfoService;
    private final PointInfoService pointInfoService;
    private final SaveBoardDataService saveBoardDataService;
    private final ProductInfoService productInfoService;
    private final ProductWishService productWishService;
    private final RecipeWishService recipeWishService;
    private final RecipeInfoService recipeInfoService;

    @GetMapping
    public String myPage(Model model) {
        commonProcess("myPage", model);

        ListData<OrderInfo> orderInfos = orderInfoService.getList(3);
        List<OrderInfo> orders = orderInfos.getItems().stream().limit(5).toList();

        for(OrderInfo order : orders){
            List<OrderItem> items = order.getOrderItems();
            for(OrderItem item : items){
                Product product = productInfoService.get(item.getProduct().getSeq());
                item.setProduct(product);
            }
        }

        model.addAttribute("point", pointInfoService.pointSum());
        model.addAttribute("orders", orders);
        return utils.tpl("member/mypage/mypage");
    }


    /**
     * 마이 다이어리
     * @param model
     * @return
     */
    @GetMapping("/diary")
    public String diary(@ModelAttribute RequestMemberInfo memberInfo, Model model, Errors errors) {
        commonProcess("diary", model);

        return utils.tpl("member/mypage/diary");
    }

    @GetMapping("/diary/content/bookmark")
    public String content(@ModelAttribute RecipeDataSearch search, Model model) {

        ListData<RecipeWish> wishes = recipeWishService.getWishRecipes(search);
        List<Recipe> recipes = wishes.getItems().stream().map(s -> s.getRecipe()).toList();


        model.addAttribute("recipes", recipes);
        model.addAttribute("pagination", wishes.getPagination());
        return utils.tpl("member/mypage/content/bookmark");
    }

    @GetMapping("/diary/content/recipe")
    public String recipe(@ModelAttribute RecipeDataSearch search, Model model){

        ListData<Recipe> recipes = recipeInfoService.getPersonalList(search);


        model.addAttribute("recipes", recipes.getItems());
        model.addAttribute("pagination", recipes.getPagination());
        return utils.tpl("member/mypage/content/recipe");
    }


    /**
     * 전체 주문내역
     * @param model
     * @return
     */
    @GetMapping("/orders")
    public String orders(Model model) {
        commonProcess("orders", model);

        ListData<OrderInfo> orderInfos = orderInfoService.getList();
        List<OrderInfo> orders = orderInfos.getItems();
        Pagination pagination = orderInfos.getPagination();

        model.addAttribute("orders", orders);
        model.addAttribute("pagination", pagination);
        return utils.tpl("member/mypage/orders");
    }

    /**
     * 마이 포인트
     * @param model
     * @return
     */
    @GetMapping("/point")
    public String point(Model model) {
        commonProcess("point", model);

        ListData<Point> point = pointInfoService.getList();

        model.addAttribute("totalPoint", pointInfoService.pointSum());
        model.addAttribute("items", point.getItems());
        model.addAttribute("pagination", point.getPagination());
        return utils.tpl("member/mypage/point");
    }

    /**
     * 최근 본 상품
     * @param model
     * @return
     */
    @GetMapping("/recentlyview")
    public String recentlyview(Model model) {
        commonProcess("recentlyview", model);
        return utils.tpl("member/mypage/recently_view");
    }

    /**
     * 찜한 상품 목록
     *
     * @param search
     * @param model
     * @return
     */
    @GetMapping("/save_post")
    public String savePost(@ModelAttribute ProductSearch search, Model model) {
        commonProcess("save_post", model);

        ListData<ProductWish> data = productWishService.getWishProducts(search);

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return utils.tpl("member/mypage/save_post");
    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "myPage";
        String pageTitle = Utils.getMessage("마이페이지", "commons");

        List<String> addCommonScript = new ArrayList<>();    // 공통 자바스크립트
        List<String> addCommonCss = new ArrayList<>();    // 공통 자바스크립트
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if(mode.equals("orders") || mode.equals("myPage")){
            pageTitle = mode.equals("orders") ? "마이페이지" : "주문 내역";

            addCss.add("member/mypage/mypage");

            addCss.add("member/mypage/order");
        }else if(mode.equals("recentlyview")){
            addScript.add("member/mypage/recently");
            addCss.add("product/style");
            pageTitle = "최근 본 상품";
        }else if(mode.equals("point")){
            pageTitle = "포인트 상세";
            addCss.add("member/mypage/point");
            addCss.add("member/mypage/mypage");
        }else if (mode.equals("diary")) {
            addCss.add("member/mypage/diary");
            addScript.add("member/mypage/diary");
        }
        addCommonScript.add("tab");
        addCommonCss.add("tab");


        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addCommonCss", addCommonCss);


        if (mode.equals("save_post")) { // 찜한 게시글 페이지
            pageTitle = Utils.getMessage("찜한상품", "commons");

            addScript.add("board/common");
            addScript.add("member/mypage/save_post");
        }
    }


}

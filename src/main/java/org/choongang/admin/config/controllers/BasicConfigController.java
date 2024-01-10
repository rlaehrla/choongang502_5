package org.choongang.admin.config.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.service.ConfigInfoService;
import org.choongang.admin.config.service.ConfigSaveService;
import org.choongang.admin.menus.AdminMenu;
import org.choongang.commons.MenuDetail;
import org.choongang.commons.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/config")
@RequiredArgsConstructor
public class BasicConfigController implements ExceptionProcessor {

    private final ConfigSaveService saveService;
    private final ConfigInfoService infoService;

    @ModelAttribute("menuCode")
    public String getMenuCode() {
        return "config";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() { // 서브 메뉴
        return AdminMenu.getMenus("config");
    }

    @GetMapping
    public String index(Model model) {

        BasicConfig config = infoService.get("basic", BasicConfig.class).orElseGet(BasicConfig::new);
        commonProcess("config", model);
        model.addAttribute("basicConfig", config);

        return "admin/config/basic";
    }

    @PostMapping
    public String save(BasicConfig config, Model model) {

        saveService.save("basic", config);

        model.addAttribute("message", "저장되었습니다.");

        return "admin/config/basic";
    }

    @GetMapping("/payment")
    public String payment(Model model) {
        commonProcess("payment", model);

        return "admin/config/payment";
    }

    /**
     * Api 설정 정보 저장
     */
    @GetMapping("/api")
    public String api(@ModelAttribute ApiConfig config, Model model) {

        config = infoService.get("apiConfig", ApiConfig.class).orElseGet(ApiConfig::new);

        model.addAttribute("apiConfig", config);

        return "admin/config/api";
    }

    @PostMapping("/api")
    public String apiSave(ApiConfig config, Model model) {

        saveService.save("apiConfig", config);

        model.addAttribute("message", "저장되었습니다.");

        return "admin/config/api";
    }

    private void commonProcess(String mode, Model model) {
        String pageTitle = "기본설정";
        mode = StringUtils.hasText(mode) ? mode : "config";

        if (mode.equals("payment")) {
            pageTitle = "결제설정";
        } else if (mode.equals("api")) {
            pageTitle = "api 설정";
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", mode);
    }
}

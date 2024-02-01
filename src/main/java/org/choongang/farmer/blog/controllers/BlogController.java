package org.choongang.farmer.blog.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.service.ConfigInfoService;
import org.choongang.admin.product.controllers.ProductSearch;
import org.choongang.board.controllers.AbstractBoardController;
import org.choongang.board.controllers.BoardDataSearch;
import org.choongang.board.controllers.BoardFormValidator;
import org.choongang.board.entities.BoardData;
import org.choongang.board.service.BoardAuthService;
import org.choongang.board.service.BoardDeleteService;
import org.choongang.board.service.BoardInfoService;
import org.choongang.board.service.BoardSaveService;
import org.choongang.board.service.config.BoardConfigInfoService;
import org.choongang.board.service.review.ReviewAuthService;
import org.choongang.commons.ListData;
import org.choongang.commons.Utils;
import org.choongang.farmer.blog.intro.BlogIntroPost;
import org.choongang.farmer.blog.service.SellingInfoService;
import org.choongang.file.service.FileInfoService;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Farmer;
import org.choongang.member.service.MemberInfo;
import org.choongang.member.service.MemberInfoService;
import org.choongang.order.service.OrderItemInfoService;
import org.choongang.product.entities.Product;
import org.choongang.product.service.ProductInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/farm/blog")
public class BlogController extends AbstractBoardController {

    public BlogController(ProductInfoService productInfoService, ConfigInfoService confInfoService, BoardConfigInfoService configInfoService, FileInfoService fileInfoService, BoardFormValidator boardFormValidator, BoardSaveService boardSaveService, BoardInfoService boardInfoService, BoardDeleteService boardDeleteService, BoardAuthService boardAuthService, ReviewAuthService reviewAuthService, OrderItemInfoService orderItemInfoService, MemberUtil memberUtil, MemberInfoService memberInfoService, Utils utils, SellingInfoService sellingInfoService, HttpServletRequest request) {
        super(productInfoService, confInfoService, configInfoService, fileInfoService, boardFormValidator, boardSaveService, boardInfoService, boardDeleteService, boardAuthService, reviewAuthService, orderItemInfoService, memberUtil, memberInfoService, utils, sellingInfoService, request);
    }

    @GetMapping("/{farmerId}")
    public String index(@PathVariable("farmerId") String farmerId,
                        @RequestParam(name="tab", defaultValue = "intro") String tab,
                        @ModelAttribute ProductSearch productSearch,
                        @ModelAttribute BoardDataSearch search, Model model) {

        // farmer 정보 가져오기
        Farmer farmerInfo = (Farmer) ((MemberInfo) memberInfoService.loadUserByUsername(farmerId)).getMember() ;

        model.addAttribute("farmer", farmerInfo) ;
        model.addAttribute("farmerCount", productInfoService.saleSum(farmerInfo));
        // 한 줄 소개 문장 가져오기
        BlogIntroPost introSum = confInfoService.get(farmerId + "_sum", BlogIntroPost.class).orElseGet(BlogIntroPost::new) ;
        model.addAttribute("introSum", introSum) ;

        List<String> addCss = new ArrayList<>() ;

        model.addAttribute("tab", tab);

        if (tab.equals("intro")) {

            BlogIntroPost intro = confInfoService.get(farmerId, BlogIntroPost.class).orElseGet(BlogIntroPost::new);
            model.addAttribute("blogIntroPost", intro);

        } else if (tab.equals("sales")) {
            ListData<Product> products = sellingInfoService.getSellingList(farmerId, productSearch) ;
            model.addAttribute("products", products.getItems()) ;
            model.addAttribute("pagination", products.getPagination()) ;

            addCss.add("product/style") ;

        } else if (tab.equals("review")) {
            String bid = "review" ;

            ListData<BoardData> data = boardInfoService.getList(bid, search);

            /* 게시판 설정 처리 */
            board = configInfoService.get(bid);
            model.addAttribute("board", board);

            model.addAttribute("items", data.getItems());
            model.addAttribute("pagination", data.getPagination());

        } else if (tab.equals("sns")) {
            // 회원 가입 -> 농장 -> 게시판 하나 생성(blog_아이디)
            String bid = "sns_" + farmerId ;
            commonProcess(bid, "list", model);

            ListData<BoardData> data = boardInfoService.getList(bid, search);
            model.addAttribute("board", board);
            model.addAttribute("items", data.getItems());
            model.addAttribute("pagination", data.getPagination());
        }
        addCss.add("blog/style");

        model.addAttribute("addCss", addCss);

        return utils.tpl("blog/index");
    }
}

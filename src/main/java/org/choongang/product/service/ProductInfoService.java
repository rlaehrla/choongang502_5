package org.choongang.product.service;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.product.controllers.ProductSearch;
import org.choongang.admin.product.controllers.RequestProduct;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Farmer;
import org.choongang.order.entities.OrderItem;
import org.choongang.order.entities.QOrderItem;
import org.choongang.order.repositories.OrderItemRepository;
import org.choongang.product.constants.MainCategory;
import org.choongang.product.constants.ProductStatus;
import org.choongang.product.entities.Product;
import org.choongang.product.entities.QProduct;
import org.choongang.product.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Iterator;
import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class ProductInfoService {

    private final ProductRepository productRepository;
    private final MemberUtil memberUtil;
    private final HttpServletRequest request;
    private final FileInfoService fileInfoService;
    private final OrderItemRepository orderItemRepository;


    public Product get(Long seq) {
        Product product = productRepository.findById(seq)
                .orElseThrow(ProductNotFoundException::new);

        addProductInfo(product);

        return product;

    }

    /**
     * 상품 목록 조회
     *
     * @param search
     * @param isAll : true - 미노출 상품도 모두 보이게
     * @param isMain : 메인페이지에서 사용하는지
     *
     * @return
     */

    public ListData<Product> getList(ProductSearch search, boolean isAll, boolean isMain){

        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20);

        QProduct product = QProduct.product;
        BooleanBuilder andBuilder = new BooleanBuilder();


        /* 검색 조건 처리 S */

        // 농부는 본인의 상품만 볼 수 있도록
        if(!isMain && memberUtil.isFarmer()){
            String userId = memberUtil.getMember().getUserId();
            andBuilder.and(product.farmer.userId.eq(userId));
        }

        List<String> cateCd = search.getCateCd();
        List<Long> seq = search.getSeq();
        List<String> status = search.getStatuses();
        LocalDate sdate = search.getSdate();
        LocalDate edate = search.getEdate();
        String name = search.getName();

        if(cateCd != null && !cateCd.isEmpty()){
            andBuilder.and(product.category.cateCd.in(cateCd));
        }

        if (seq != null && !seq.isEmpty()){
            andBuilder.and(product.seq.in(seq));
        }

        if(status != null && !status.isEmpty()){
            List<ProductStatus> _statuses = status.stream().map(ProductStatus::valueOf).toList();
            andBuilder.and(product.status.in(_statuses));
        }

        if(sdate != null){
            andBuilder.and(product.createdAt.goe(LocalDateTime.of(sdate, LocalTime.of(0, 0, 0))));
        }

        if (edate != null){
            andBuilder.and(product.createdAt.loe(LocalDateTime.of(edate, LocalTime.of(23, 59, 59))));
        }

        if(StringUtils.hasText(name)){

            andBuilder.and(product.name.contains(name.trim()));
        }

        if(!isAll){

            andBuilder.and(product.active.eq(true));
        }

        /* 검색 조건 처리 E */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<Product> data = productRepository.findAll(andBuilder, pageable);

        Pagination pagination = new Pagination(page, (int) data.getTotalElements(), 10, limit, request);

        List<Product> items = data.getContent();
        items.forEach(this::addProductInfo);


        return new ListData<>(items, pagination);

    }

    /**
     * isAll 입력 안하면 모든 상품 보이게
     * @param search
     * @return
     */
    public ListData<Product> getList(ProductSearch search){
        return getList(search, true, false);
    }

    /**
     * file 저장
     * @param product
     */

    public void addProductInfo(Product product){
       String gid = product.getGid();

       List<FileInfo> editorImages = fileInfoService.getListDone(gid, "description");
       List<FileInfo> mainImages = fileInfoService.getListDone(gid, "product_main");
       List<FileInfo> listImages = fileInfoService.getListDone(gid, "product_list");

       product.setEditorImages(editorImages);
       product.setMainImages(mainImages);
       product.setListImages(listImages);


    }

    public RequestProduct getForm(Long seq){

        Product product = get(seq);
        RequestProduct form = new ModelMapper().map(product, RequestProduct.class);

        form.setFarmer(product.getFarmer().getUserId());
        form.setCateCd(product.getCategory().getCateCd());

        form.setMode("edit");


        return form;
    }

    /**
     * 미노출, 판매중이 아닌 상품을 제외하고 상품 목록 반환
     * --> 홈페이지의 상품 목록 출력할 때!
     */
    public ListData<Product> getProducts(MainCategory category, ProductSearch search) {

        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20);

        QProduct product = QProduct.product;
        BooleanBuilder andBuilder = new BooleanBuilder();

        /* 검색 조건 처리 S */

        List<String> cateCd = search.getCateCd();
        List<Long> seq = search.getSeq();
        List<String> status = search.getStatuses();
        LocalDate sdate = search.getSdate();
        LocalDate edate = search.getEdate();
        String name = search.getName();

        if (category != null) {
            andBuilder.and(product.category.mainCategory.eq(category)) ;
        }

        if(cateCd != null && !cateCd.isEmpty()){
            andBuilder.and(product.category.cateCd.in(cateCd));
        }

        if (seq != null && !seq.isEmpty()){
            andBuilder.and(product.seq.in(seq));
        }


        if(sdate != null){
            andBuilder.and(product.createdAt.goe(LocalDateTime.of(sdate, LocalTime.of(0, 0, 0))));
        }

        if (edate != null){
            andBuilder.and(product.createdAt.loe(LocalDateTime.of(edate, LocalTime.of(23, 59, 59))));
        }

        if(StringUtils.hasText(name)){

            andBuilder.and(product.name.contains(name.trim()));
        }
        // 노출중인 상품만
        andBuilder.and(product.active.eq(true));
        // 준비중인 상품은 노출 안되게
        andBuilder.and(product.status.ne(ProductStatus.PREPARE));


        /* 검색 조건 처리 E */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<Product> data = productRepository.findAll(andBuilder, pageable);

        Pagination pagination = new Pagination(page, (int) data.getTotalElements(), 10, limit, request);

        List<Product> items = data.getContent();
        items.forEach(this::addProductInfo);


        return new ListData<>(items, pagination);
    }

    /**
     * 현재 월에 제철인 상품 목록 조회
     *
     * @return 현재 월에 제철인 상품 목록
     */
    public List<Product> getInSeasonProducts() {
        int currentMonth = LocalDate.now().getMonthValue();

        QProduct product = QProduct.product;
        BooleanBuilder andBuilder = new BooleanBuilder();

        // category의 months 필드에 현재 월이 포함되어 있는 경우를 필터링
        andBuilder.and(product.category.months.any().eq(Month.of(currentMonth)));

        List<Product> inSeasonProducts = (List<Product>) productRepository.findAll(andBuilder);

        inSeasonProducts.forEach(this::addProductInfo);

        return inSeasonProducts;
    }

    /**
     * 농부별 판매량 합계
     *
     * @param farmer
     * @return
     */
    public long saleSum(Farmer farmer){
        QOrderItem orderItem = QOrderItem.orderItem;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(orderItem.product.farmer.eq(farmer));

        Iterator<OrderItem> orderItems = orderItemRepository.findAll(builder).iterator();
        long sum = 0L;

        while(orderItems.hasNext()){
            OrderItem orderItem1 = orderItems.next();
            sum += orderItem1.getEa();
        }

      return sum;
    }

}

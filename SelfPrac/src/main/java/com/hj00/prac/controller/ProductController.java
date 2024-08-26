package com.hj00.prac.controller;

import com.hj00.prac.command.ProductVO;
import com.hj00.prac.product.ProductService;
import com.hj00.prac.util.Criteria;
import com.hj00.prac.util.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    @Qualifier("productService")
    public ProductService productService;


    //STEP1. criteria 같은 객체에 검색 키워드 추가
    //STEP2. 목록 sql, 토탈게시글 sql 동적쿼리로 변경
    //STEP3. 화면에서 사용자가 검색버튼을 누를때, 다시 PAGE 번호를 1번으로 , amount 를 유지
    //STEP4. 검색값의 유지 (criteria 안에 있음 ) - 검색 버튼을 한번 누르면 기존 검색이 사라짐
    //STEP5. 페이지네이션 누를 때, 검색 키워드를 같이 넘겨주어야 함
    //STEP6. 40개씩 보기 버튼 처리

    //목록
    @GetMapping("/productList")
    public String productList(Model model, Criteria cri) {
        String userId = "admin";
        ArrayList<ProductVO> list = productService.getList(userId, cri);

        model.addAttribute("list", list);
        System.out.println(list.toString());

        //페이지VO
        int total = productService.getTotal(userId, cri);
        PageVO pageVO = new PageVO(cri,total );
        model.addAttribute("pageVO", pageVO);
        return "product/productList";
    }

    //등록
    @GetMapping("/productReg")
    public String productReg() {
        return "product/productReg";
    }

    //상세
    @GetMapping("/productDetail")
    public String productDetail(@RequestParam("prodId") int prodId,
                                Model model) {
        ProductVO vo = productService.getDetail(prodId);
        model.addAttribute("vo", vo);

        return "product/productDetail";
    }


    //등록요청
    @PostMapping("/registForm")
    public String registForm(ProductVO vo, RedirectAttributes ra) {
        //System.out.println(vo.toString());
        //서버측에서 유효성 검사 진행가능
        int result = productService.productInsert(vo);
        //System.out.println("결과" + result);
        if (result == 1) {
            ra.addFlashAttribute("msg", "정상등록되었습니다");
        } else {
            ra.addFlashAttribute("msg", "등록에 실패했습니다. 문의에 남겨주세요");
        }
        return "redirect:/product/productList"; //나가는 경로 //리다이렉트를 적지 않으면 목록이 출력이 안됨
    }

    //상품 수정기능 - 반드시 필요한 값은 pk
    @PostMapping("/productUpdate")
    public String productUpdate(ProductVO vo,
                                RedirectAttributes ra) {
        //System.out.println(vo.toString());
        //업데이트
        int result = productService.productUpdate(vo);
        if(result == 1) {
            ra.addFlashAttribute("msg", "수정되었습니다");
        } else {
            ra.addFlashAttribute("msg", "수정에 실패했습니다");
        }

        return "redirect:/product/productDetail?prodId=" + vo.getProdId(); //상세화면은 id값을 필요로 함
    }

    //삭제
    @PostMapping("/productDelete")
    public String productDelete(@RequestParam("prodId") int prodId) {
        productService.productDelete(prodId);
        System.out.println(prodId);
        return "redirect:/product/productList";
    }
}

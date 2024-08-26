package com.hj00.prac.product;

import com.hj00.prac.command.ProductVO;
import com.hj00.prac.util.Criteria;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface ProductService {

    public int productInsert(ProductVO vo); //등록
    public ArrayList<ProductVO> getList(String userId, Criteria cri); //목록
    public int getTotal(@Param("userId") String userId, @Param("cri") Criteria cri);
    public ProductVO getDetail(int prodId); //상세내역
    public int productUpdate(ProductVO vo); //수정
    public void productDelete(int prodId); //삭제
}

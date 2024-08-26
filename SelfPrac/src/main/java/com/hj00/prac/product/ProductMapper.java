package com.hj00.prac.product;

import com.hj00.prac.command.ProductVO;
import com.hj00.prac.util.Criteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface ProductMapper {

    public int productInsert(ProductVO vo); //등록
    public ArrayList<ProductVO> getList(@Param("userId") String userId, @Param("cri") Criteria cri); //목록 (매개변수가 2개 이상이면, 2개의 이름을 붙여줘야함)
    public int getTotal(@Param("userId") String userId, @Param("cri") Criteria cri); //전체 게시글
    public ProductVO getDetail(int prodId); //상세내역
    public int productUpdate(ProductVO vo); //수정
    public void productDelete(int prodId); //삭제
}

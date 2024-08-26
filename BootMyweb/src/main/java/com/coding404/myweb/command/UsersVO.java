package com.coding404.myweb.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersVO<T> {
    private String id;
    private String pw;
    private String name;

    //1:N
    //N 쪽 데이터를 list 로 담는다
    private ArrayList<T> memoList;
}

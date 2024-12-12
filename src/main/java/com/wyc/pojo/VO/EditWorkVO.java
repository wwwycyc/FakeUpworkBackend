package com.wyc.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditWorkVO {
    private int workId;
    private String title;
    private String introduction;
    private String cover;
    private String content;
    private Integer needId;
    private Integer partId;
    private List<Integer> talentIds;
}

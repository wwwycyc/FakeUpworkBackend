package com.wyc.pojo.DTO;

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
public class UpdateWorkDTO {
    private int workId;
    private String title;
    private String introduction;
    private String cover;
    private String content;
    private LocalDate postDate;
    private List<Integer> talentIds;
    private int needId;
    private int partId;
}

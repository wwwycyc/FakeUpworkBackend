package com.wyc.pojo.DTO;

import com.wyc.pojo.Entity.Work;
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
public class PostWorkDTO {
    private int workId;
    private String title;
    private String introduction;
    private String cover;
    private String content;
    private String poster;
    private LocalDate postDate;
    private int state;//1 accepted 0.not accepted
    private List<Integer> talentIds;
    private int needId;
    private int partId;
}

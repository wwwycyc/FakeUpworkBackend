package com.wyc.pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkCard implements Serializable {
    private int workId;
    private String title;
    private String introduction;
    private String cover;
    private String content;
    private String poster;
    private String accepter;
    private LocalDate postDate;
    private LocalDate acceptDate;
    private int state;//1 accepted 0.not accepted
    private int talentId;
}

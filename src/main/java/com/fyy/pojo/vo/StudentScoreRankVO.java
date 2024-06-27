package com.fyy.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @date 2024-06-27 15:42:48
 * @description
 */
@Data
public class StudentScoreRankVO {
    @ApiModelProperty(value = "语文排名")
    private Integer chineseRank;
    @ApiModelProperty(value = "数学排名")
    private Integer mathRank;
    @ApiModelProperty(value = "英语排名")
    private Integer englishRank;
    @ApiModelProperty(value = "物理排名")
    private Integer physicsRank;
    @ApiModelProperty(value = "化学排名")
    private Integer chemistryRank;
    @ApiModelProperty(value = "历史排名")
    private Integer historyRank;
    @ApiModelProperty(value = "政治排名")
    private Integer politicsRank;
    @ApiModelProperty(value = "地理排名")
    private Integer geographyRank;
    @ApiModelProperty(value = "生物排名")
    private Integer biologyRank;
    @ApiModelProperty(value = "总分排名")
    private Integer totalScoreRank;
}

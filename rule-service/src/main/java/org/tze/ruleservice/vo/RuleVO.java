package org.tze.ruleservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RuleVO {

    private Long id;

    private String name;
    private String description;

    private Long projectId;

    private List<TriggerVO> triggers;  // 不同触发器之间是或的关系，触发器中的条件需要同时满足
    private List<ActionVO> actions;

    private LocalDateTime begin;
    private LocalDateTime end;

    private String drl;

    private boolean enabled;

}

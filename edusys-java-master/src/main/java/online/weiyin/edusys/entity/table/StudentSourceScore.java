package online.weiyin.edusys.entity.table;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname StudentScore
 * @description studentscore_xxx实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table("StudentSource_score")
public class StudentSourceScore {
    @Column("id")
    private String id;
    @Column("group_score")
    private Double groupScore;
    @Column("weapon_score")
    private Double weaponScore;
    @Column("tactical_score")
    private Double tacticalScore;
    @Column("fight_score")
    private Double fightScore;
    @Column("rescue_score")
    private Double rescueScore;
    @Column("nuclear_score")
    private Double nuclearScore;
    @Column("run_score")
    private Double runScore;
    @Column("count_score")
    private Double countScore;
}

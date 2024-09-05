package easyms.statemachine;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface StateMachineRepository extends JpaRepository<StateMachineEntity, Long> {
    StateMachineEntity findByConductIdAndValueDate(Long conductId, LocalDate valueDate);
}


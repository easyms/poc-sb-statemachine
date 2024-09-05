package easyms.statemachine;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StateMachineService {

    @Autowired
    private StateMachineFactory<States, Events> stateMachineFactory;

    @Autowired
    private StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister;

    @Autowired
    private StateMachineRepository stateMachineRepository;

    public StateMachine<States, Events> acquireStateMachine(Long conductId, LocalDate valueDate) {
        String stateMachineId = getStateMachineId(conductId, valueDate);
        StateMachineEntity entity = stateMachineRepository.findByConductIdAndValueDate(conductId, valueDate);

        if (entity == null) {
            entity = new StateMachineEntity();
            entity.setConductId(conductId);
            entity.setValueDate(valueDate);
            entity.setStateMachineId(stateMachineId);
            stateMachineRepository.save(entity);
        }

        StateMachine<States, Events> stateMachine = stateMachineFactory.getStateMachine(stateMachineId);
        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(access -> access.addStateMachineInterceptor(stateMachineRuntimePersister.getInterceptor()));
        stateMachine.start();
        return stateMachine;
    }

    private String getStateMachineId(Long conductId, LocalDate valueDate) {
        return conductId + "_" + valueDate.toString();
    }
}


package easyms.statemachine;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
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
    private JpaStateMachineRepository jpaStateMachineRepository;  // Provided by Spring State Machine



    private String buildStateMachineId(Long conductId, LocalDate valueDate) {
        return conductId + "_" + valueDate.toString();
    }

    public StateMachine<States, Events> acquireStateMachine(Long conductId, LocalDate valueDate) {
        String machineId = buildStateMachineId(conductId, valueDate);
        // Step 1: Get the state machine from the factory (initial state)
        StateMachine<States, Events> stateMachine = stateMachineFactory.getStateMachine(machineId);

        // Step 2: Attach the runtime persister to restore the persisted state if it exists
        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(access -> access.addStateMachineInterceptor(stateMachineRuntimePersister.getInterceptor()));

        // Step 3: Start the state machine, which triggers the persister to restore the state
        stateMachine.start();

        return stateMachine;
    }


}


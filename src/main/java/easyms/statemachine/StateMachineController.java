package easyms.statemachine;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/state-machine")
public class StateMachineController {

    @Autowired
    private StateMachineService stateMachineService;

    @GetMapping("/collect-requested")
    public String collectRequested(@RequestParam Long conductId, @RequestParam String valueDate) {
        LocalDate date = LocalDate.parse(valueDate);
        StateMachine<States, Events> stateMachine = stateMachineService.acquireStateMachine(conductId, date);
        stateMachine.sendEvent(Events.COLLECT_REQUESTED);
        return "COLLECT_REQUESTED event sent for ConductId: " + conductId + " and ValueDate: " + valueDate;
    }
}


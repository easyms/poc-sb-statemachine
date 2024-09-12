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

    @GetMapping("/collect-ok")
    public String collectOk(@RequestParam Long conductId, @RequestParam String valueDate) {
        LocalDate date = LocalDate.parse(valueDate);
        StateMachine<States, Events> stateMachine = stateMachineService.acquireStateMachine(conductId, date);
        stateMachine.sendEvent(Events.COLLECT_OK);
        return "COLLECT_OK event sent";
    }

    @GetMapping("/elig-ok")
    public String eligOk(@RequestParam Long conductId, @RequestParam String valueDate) {
        LocalDate date = LocalDate.parse(valueDate);
        StateMachine<States, Events> stateMachine = stateMachineService.acquireStateMachine(conductId, date);
        stateMachine.sendEvent(Events.ELIG_OK);
        return "ELIG_OK event sent";
    }

    @GetMapping("/ptf-ok")
    public String ptfOk(@RequestParam Long conductId, @RequestParam String valueDate) {
        LocalDate date = LocalDate.parse(valueDate);
        StateMachine<States, Events> stateMachine = stateMachineService.acquireStateMachine(conductId, date);
        stateMachine.sendEvent(Events.PTF_OK);
        return "PTF_OK event sent";
    }

    @GetMapping("/mob-ok")
    public String mobOk(@RequestParam Long conductId, @RequestParam String valueDate) {
        LocalDate date = LocalDate.parse(valueDate);
        StateMachine<States, Events> stateMachine = stateMachineService.acquireStateMachine(conductId, date);
        stateMachine.sendEvent(Events.MOB_OK);
        return "MOB_OK event sent";
    }
}




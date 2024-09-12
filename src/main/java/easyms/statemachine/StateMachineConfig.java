package easyms.statemachine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends StateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
                .withStates()
                .initial(States.Initial)
                .state(States.ConductCollectRequested)
                .state(States.ConductCollectDone, autoRequestEligibility())
                .state(States.ConductEligibilityComputingRequested)
                .state(States.ConductEligibilityComputingDone, autoRequestPtfGeneration())
                .state(States.ConductPortfolioGenerationRequested)
                .state(States.ConductPortfolioGenerationDone, autoRequestMob())
                .state(States.ConductMobilizationRequested)
                .end(States.ConductMobilizationDone);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                .source(States.Initial).target(States.ConductCollectRequested).event(Events.COLLECT_REQUESTED).action(requestCollect())
                .and()
                .withExternal()
                .source(States.ConductCollectRequested).target(States.ConductCollectDone).event(Events.COLLECT_OK)
                .and()
                .withExternal()
                .source(States.ConductCollectDone).target(States.ConductEligibilityComputingRequested).event(Events.ELIG_COMPUTE_REQUESTED).action(requestEligComputing())
                .and()
                .withExternal()
                .source(States.ConductEligibilityComputingRequested).target(States.ConductEligibilityComputingDone).event(Events.ELIG_OK)
                .and()
                .withExternal()
                .source(States.ConductEligibilityComputingDone).target(States.ConductPortfolioGenerationRequested).event(Events.PTF_COMPUTE_REQUESTED).action(requestPtfComputing())
                .and()
                .withExternal()
                .source(States.ConductPortfolioGenerationRequested).target(States.ConductPortfolioGenerationDone).event(Events.PTF_OK)
                .and()
                .withExternal()
                .source(States.ConductPortfolioGenerationDone).target(States.ConductMobilizationRequested).event(Events.MOB_COMPUTE_REQUESTED).action(requestMob())
                .and()
                .withExternal()
                .source(States.ConductMobilizationRequested).target(States.ConductMobilizationDone).event(Events.MOB_OK);
    }

    @Bean
    public Action<States, Events> requestCollect() {
        return context -> {
            System.out.println("Action: requestCollect");
            // Logique de l'action requestCollect
        };
    }

    @Bean
    public Action<States, Events> requestEligComputing() {
        return context -> {
            System.out.println("Action: requestEligComputing");
            // Logique de l'action requestEligComputing
        };
    }

    @Bean
    public Action<States, Events> requestPtfComputing() {
        return context -> {
            System.out.println("Action: requestPtfComputing");
            // Logique de l'action requestPtfComputing
        };
    }

    @Bean
    public Action<States, Events> requestMob() {
        return context -> {
            System.out.println("Action: requestMob");
            // Logique de l'action requestMob
        };
    }

    @Bean
    public Action<States, Events> autoRequestEligibility() {
        return context -> {
            System.out.println("Action: autoRequestEligibility - fire ELIG_COMPUTE_REQUESTED");
            context.getStateMachine().sendEvent(Events.ELIG_COMPUTE_REQUESTED);
        };
    }

    @Bean
    public Action<States, Events> autoRequestPtfGeneration() {
        return context -> {
            System.out.println("Action: autoRequestPtfGeneration - fire PTF_COMPUTE_REQUESTED");
            context.getStateMachine().sendEvent(Events.PTF_COMPUTE_REQUESTED);
        };
    }

    @Bean
    public Action<States, Events> autoRequestMob() {
        return context -> {
            System.out.println("Action: autoRequestMob - fire MOB_COMPUTE_REQUESTED");
            context.getStateMachine().sendEvent(Events.MOB_COMPUTE_REQUESTED);
        };
    }

    @Bean
    public StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister(
            JpaStateMachineRepository jpaStateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
    }
}



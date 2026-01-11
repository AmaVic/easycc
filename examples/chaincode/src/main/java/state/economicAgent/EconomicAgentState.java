package state.economicAgent;

import entity.EconomicAgent;

import runtime.State;
import runtime.exception.FailedEventHandlingException;

import org.hyperledger.fabric.contract.Context;

public abstract class EconomicAgentState extends State {
  //========== Constructor ===========
  public EconomicAgentState(String name, StateType stateType) {
    super(name, stateType);
  }

  //========= Event Handling =========
  public void handle_EVcrDecrement(EconomicAgent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrDecrement(EconomicAgent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrIncrement(EconomicAgent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrIncrement(EconomicAgent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendIncrement(EconomicAgent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendIncrement(EconomicAgent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrDependentView(EconomicAgent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrDependentView(EconomicAgent, Context)]: Transition not allowed from the current state"); }
  public void handle_endLastDependenctView(EconomicAgent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_endLastDependenctView(EconomicAgent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrParticipation(EconomicAgent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrParticipation(EconomicAgent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendDependentView(EconomicAgent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendDependentView(EconomicAgent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrOwnership(EconomicAgent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrOwnership(EconomicAgent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrEconomicAgent(EconomicAgent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrEconomicAgent(EconomicAgent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendOwnership(EconomicAgent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendOwnership(EconomicAgent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendParticipation(EconomicAgent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendParticipation(EconomicAgent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendDecrement(EconomicAgent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendDecrement(EconomicAgent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendEconomicAgent(EconomicAgent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendEconomicAgent(EconomicAgent, Context)]: Transition not allowed from the current state"); }
}
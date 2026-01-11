package state.economicEvent;

import entity.EconomicEvent;

import runtime.State;
import runtime.exception.FailedEventHandlingException;

import org.hyperledger.fabric.contract.Context;

public abstract class EconomicEventState extends State {
  //========== Constructor ===========
  public EconomicEventState(String name, StateType stateType) {
    super(name, stateType);
  }

  //========= Event Handling =========
  public void handle_EVcrDecrement(EconomicEvent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrDecrement(EconomicEvent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrDuality(EconomicEvent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrDuality(EconomicEvent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrIncrement(EconomicEvent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrIncrement(EconomicEvent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendIncrement(EconomicEvent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendIncrement(EconomicEvent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrDependentView(EconomicEvent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrDependentView(EconomicEvent, Context)]: Transition not allowed from the current state"); }
  public void handle_endLastDependenctView(EconomicEvent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_endLastDependenctView(EconomicEvent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrParticipation(EconomicEvent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrParticipation(EconomicEvent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendDependentView(EconomicEvent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendDependentView(EconomicEvent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrStockFlow(EconomicEvent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrStockFlow(EconomicEvent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendEconomicEvent(EconomicEvent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendEconomicEvent(EconomicEvent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendStockFlow(EconomicEvent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendStockFlow(EconomicEvent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendDuality(EconomicEvent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendDuality(EconomicEvent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendParticipation(EconomicEvent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendParticipation(EconomicEvent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrEconomicEvent(EconomicEvent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrEconomicEvent(EconomicEvent, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendDecrement(EconomicEvent object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendDecrement(EconomicEvent, Context)]: Transition not allowed from the current state"); }
}
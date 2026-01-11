package state.duality;

import entity.Duality;

import runtime.State;
import runtime.exception.FailedEventHandlingException;

import org.hyperledger.fabric.contract.Context;

public abstract class DualityState extends State {
  //========== Constructor ===========
  public DualityState(String name, StateType stateType) {
    super(name, stateType);
  }

  //========= Event Handling =========
  public void handle_EVcrDecrement(Duality object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrDecrement(Duality, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrDuality(Duality object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrDuality(Duality, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendDuality(Duality object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendDuality(Duality, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrIncrement(Duality object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrIncrement(Duality, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendIncrement(Duality object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendIncrement(Duality, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrDependentView(Duality object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrDependentView(Duality, Context)]: Transition not allowed from the current state"); }
  public void handle_endLastDependenctView(Duality object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_endLastDependenctView(Duality, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendDependentView(Duality object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendDependentView(Duality, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendDecrement(Duality object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendDecrement(Duality, Context)]: Transition not allowed from the current state"); }
}
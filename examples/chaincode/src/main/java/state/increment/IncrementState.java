package state.increment;

import entity.Increment;

import runtime.State;
import runtime.exception.FailedEventHandlingException;

import org.hyperledger.fabric.contract.Context;

public abstract class IncrementState extends State {
  //========== Constructor ===========
  public IncrementState(String name, StateType stateType) {
    super(name, stateType);
  }

  //========= Event Handling =========
  public void handle_EVcrIncrement(Increment object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrIncrement(Increment, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendIncrement(Increment object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendIncrement(Increment, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrDependentView(Increment object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrDependentView(Increment, Context)]: Transition not allowed from the current state"); }
  public void handle_endLastDependenctView(Increment object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_endLastDependenctView(Increment, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendDependentView(Increment object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendDependentView(Increment, Context)]: Transition not allowed from the current state"); }
}
package state.decrement;

import entity.Decrement;

import runtime.State;
import runtime.exception.FailedEventHandlingException;

import org.hyperledger.fabric.contract.Context;

public abstract class DecrementState extends State {
  //========== Constructor ===========
  public DecrementState(String name, StateType stateType) {
    super(name, stateType);
  }

  //========= Event Handling =========
  public void handle_EVcrDecrement(Decrement object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrDecrement(Decrement, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrDependentView(Decrement object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrDependentView(Decrement, Context)]: Transition not allowed from the current state"); }
  public void handle_endLastDependenctView(Decrement object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_endLastDependenctView(Decrement, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendDependentView(Decrement object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendDependentView(Decrement, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendDecrement(Decrement object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendDecrement(Decrement, Context)]: Transition not allowed from the current state"); }
}
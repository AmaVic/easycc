package state.ownership;

import entity.Ownership;

import runtime.State;
import runtime.exception.FailedEventHandlingException;

import org.hyperledger.fabric.contract.Context;

public abstract class OwnershipState extends State {
  //========== Constructor ===========
  public OwnershipState(String name, StateType stateType) {
    super(name, stateType);
  }

  //========= Event Handling =========
  public void handle_EVcrDecrement(Ownership object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrDecrement(Ownership, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendOwnership(Ownership object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendOwnership(Ownership, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrIncrement(Ownership object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrIncrement(Ownership, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendIncrement(Ownership object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendIncrement(Ownership, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrDependentView(Ownership object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrDependentView(Ownership, Context)]: Transition not allowed from the current state"); }
  public void handle_endLastDependenctView(Ownership object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_endLastDependenctView(Ownership, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendDependentView(Ownership object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendDependentView(Ownership, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrOwnership(Ownership object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrOwnership(Ownership, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendDecrement(Ownership object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendDecrement(Ownership, Context)]: Transition not allowed from the current state"); }
}
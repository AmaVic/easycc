package state.dependentView;

import entity.DependentView;

import runtime.State;
import runtime.exception.FailedEventHandlingException;

import org.hyperledger.fabric.contract.Context;

public abstract class DependentViewState extends State {
  //========== Constructor ===========
  public DependentViewState(String name, StateType stateType) {
    super(name, stateType);
  }

  //========= Event Handling =========
  public void handle_EVcrDependentView(DependentView object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrDependentView(DependentView, Context)]: Transition not allowed from the current state"); }
  public void handle_endLastDependenctView(DependentView object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_endLastDependenctView(DependentView, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendDependentView(DependentView object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendDependentView(DependentView, Context)]: Transition not allowed from the current state"); }
}
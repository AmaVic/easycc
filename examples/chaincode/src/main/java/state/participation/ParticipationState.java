package state.participation;

import entity.Participation;

import runtime.State;
import runtime.exception.FailedEventHandlingException;

import org.hyperledger.fabric.contract.Context;

public abstract class ParticipationState extends State {
  //========== Constructor ===========
  public ParticipationState(String name, StateType stateType) {
    super(name, stateType);
  }

  //========= Event Handling =========
  public void handle_EVcrDecrement(Participation object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrDecrement(Participation, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrIncrement(Participation object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrIncrement(Participation, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendParticipation(Participation object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendParticipation(Participation, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendIncrement(Participation object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendIncrement(Participation, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrDependentView(Participation object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrDependentView(Participation, Context)]: Transition not allowed from the current state"); }
  public void handle_endLastDependenctView(Participation object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_endLastDependenctView(Participation, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrParticipation(Participation object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrParticipation(Participation, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendDependentView(Participation object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendDependentView(Participation, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendDecrement(Participation object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendDecrement(Participation, Context)]: Transition not allowed from the current state"); }
}
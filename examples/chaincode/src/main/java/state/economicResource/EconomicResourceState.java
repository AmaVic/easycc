package state.economicResource;

import entity.EconomicResource;

import runtime.State;
import runtime.exception.FailedEventHandlingException;

import org.hyperledger.fabric.contract.Context;

public abstract class EconomicResourceState extends State {
  //========== Constructor ===========
  public EconomicResourceState(String name, StateType stateType) {
    super(name, stateType);
  }

  //========= Event Handling =========
  public void handle_EVcrDecrement(EconomicResource object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrDecrement(EconomicResource, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrIncrement(EconomicResource object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrIncrement(EconomicResource, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendIncrement(EconomicResource object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendIncrement(EconomicResource, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrDependentView(EconomicResource object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrDependentView(EconomicResource, Context)]: Transition not allowed from the current state"); }
  public void handle_endLastDependenctView(EconomicResource object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_endLastDependenctView(EconomicResource, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendDependentView(EconomicResource object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendDependentView(EconomicResource, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrStockFlow(EconomicResource object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrStockFlow(EconomicResource, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrOwnership(EconomicResource object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrOwnership(EconomicResource, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendEconomicResource(EconomicResource object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendEconomicResource(EconomicResource, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendStockFlow(EconomicResource object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendStockFlow(EconomicResource, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendOwnership(EconomicResource object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendOwnership(EconomicResource, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendDecrement(EconomicResource object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendDecrement(EconomicResource, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrEconomicResource(EconomicResource object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrEconomicResource(EconomicResource, Context)]: Transition not allowed from the current state"); }
}
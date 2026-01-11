package state.stockFlow;

import entity.StockFlow;

import runtime.State;
import runtime.exception.FailedEventHandlingException;

import org.hyperledger.fabric.contract.Context;

public abstract class StockFlowState extends State {
  //========== Constructor ===========
  public StockFlowState(String name, StateType stateType) {
    super(name, stateType);
  }

  //========= Event Handling =========
  public void handle_EVcrDecrement(StockFlow object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrDecrement(StockFlow, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendStockFlow(StockFlow object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendStockFlow(StockFlow, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrIncrement(StockFlow object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrIncrement(StockFlow, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendIncrement(StockFlow object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendIncrement(StockFlow, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrDependentView(StockFlow object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrDependentView(StockFlow, Context)]: Transition not allowed from the current state"); }
  public void handle_endLastDependenctView(StockFlow object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_endLastDependenctView(StockFlow, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendDependentView(StockFlow object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendDependentView(StockFlow, Context)]: Transition not allowed from the current state"); }
  public void handle_EVcrStockFlow(StockFlow object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVcrStockFlow(StockFlow, Context)]: Transition not allowed from the current state"); }
  public void handle_EVendDecrement(StockFlow object, Context ctx) throws FailedEventHandlingException { throw new FailedEventHandlingException("["+this.getClass().getSimpleName()+".handle_EVendDecrement(StockFlow, Context)]: Transition not allowed from the current state"); }
}
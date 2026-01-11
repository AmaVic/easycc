package state.stockFlow;

import entity.StockFlow;
import entity.EconomicResource;
import entity.EconomicEvent;

import runtime.StubHelper;
import runtime.BusinessObject;
import runtime.exception.FailedEventHandlingException;
import runtime.exception.BusinessObjectNotFoundException;
import org.bouncycastle.util.encoders.Base64;


import org.hyperledger.fabric.contract.Context;

import java.util.function.Supplier;

public class StockFlowInflowState extends StockFlowState {
  //========== Constructor ===========
  public StockFlowInflowState() {
    super("inflow", StateType.ONGOING);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  //--- Modifying Events ---
  @Override
  public void handle_EVendDependentView(StockFlow object, Context ctx) throws FailedEventHandlingException {

    //The StockFlow object will be saved with its current properties
    //Before changing state and (possibly) attributes, we check there was no change to the masters
    StockFlow currentLedgerObject = (StockFlow)StubHelper.findBusinessObject(ctx, object.getId());
    if(!object.getEconomicResourceId_EconomicResource_StockFlow().equals(currentLedgerObject.getEconomicResourceId_EconomicResource_StockFlow()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getEconomicEventId_EconomicEvent_StockFlow().equals(currentLedgerObject.getEconomicEventId_EconomicEvent_StockFlow()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    //Changes the state of the current StockFlow (object)
    StockFlowInflowState newState = new StockFlowInflowState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //EconomicResource
    EconomicResource economicResource_EconomicResource_StockFlow = (EconomicResource)StubHelper.findBusinessObject(ctx, object.getEconomicResourceId_EconomicResource_StockFlow());
    economicResource_EconomicResource_StockFlow.getCurrentState().handle_EVendDependentView(economicResource_EconomicResource_StockFlow, ctx);
    //EconomicEvent
    EconomicEvent economicEvent_EconomicEvent_StockFlow = (EconomicEvent)StubHelper.findBusinessObject(ctx, object.getEconomicEventId_EconomicEvent_StockFlow());
    economicEvent_EconomicEvent_StockFlow.getCurrentState().handle_EVendDependentView(economicEvent_EconomicEvent_StockFlow, ctx);
  }
  @Override
  public void handle_EVendIncrement(StockFlow object, Context ctx) throws FailedEventHandlingException {

    //The StockFlow object will be saved with its current properties
    //Before changing state and (possibly) attributes, we check there was no change to the masters
    StockFlow currentLedgerObject = (StockFlow)StubHelper.findBusinessObject(ctx, object.getId());
    if(!object.getEconomicResourceId_EconomicResource_StockFlow().equals(currentLedgerObject.getEconomicResourceId_EconomicResource_StockFlow()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getEconomicEventId_EconomicEvent_StockFlow().equals(currentLedgerObject.getEconomicEventId_EconomicEvent_StockFlow()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    //Changes the state of the current StockFlow (object)
    StockFlowUseState newState = new StockFlowUseState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //EconomicResource
    EconomicResource economicResource_EconomicResource_StockFlow = (EconomicResource)StubHelper.findBusinessObject(ctx, object.getEconomicResourceId_EconomicResource_StockFlow());
    economicResource_EconomicResource_StockFlow.getCurrentState().handle_EVendIncrement(economicResource_EconomicResource_StockFlow, ctx);
    //EconomicEvent
    EconomicEvent economicEvent_EconomicEvent_StockFlow = (EconomicEvent)StubHelper.findBusinessObject(ctx, object.getEconomicEventId_EconomicEvent_StockFlow());
    economicEvent_EconomicEvent_StockFlow.getCurrentState().handle_EVendIncrement(economicEvent_EconomicEvent_StockFlow, ctx);
  }
  @Override
  public void handle_EVcrDependentView(StockFlow object, Context ctx) throws FailedEventHandlingException {

    //The StockFlow object will be saved with its current properties
    //Before changing state and (possibly) attributes, we check there was no change to the masters
    StockFlow currentLedgerObject = (StockFlow)StubHelper.findBusinessObject(ctx, object.getId());
    if(!object.getEconomicResourceId_EconomicResource_StockFlow().equals(currentLedgerObject.getEconomicResourceId_EconomicResource_StockFlow()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getEconomicEventId_EconomicEvent_StockFlow().equals(currentLedgerObject.getEconomicEventId_EconomicEvent_StockFlow()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    //Changes the state of the current StockFlow (object)
    StockFlowInflowState newState = new StockFlowInflowState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //EconomicResource
    EconomicResource economicResource_EconomicResource_StockFlow = (EconomicResource)StubHelper.findBusinessObject(ctx, object.getEconomicResourceId_EconomicResource_StockFlow());
    economicResource_EconomicResource_StockFlow.getCurrentState().handle_EVcrDependentView(economicResource_EconomicResource_StockFlow, ctx);
    //EconomicEvent
    EconomicEvent economicEvent_EconomicEvent_StockFlow = (EconomicEvent)StubHelper.findBusinessObject(ctx, object.getEconomicEventId_EconomicEvent_StockFlow());
    economicEvent_EconomicEvent_StockFlow.getCurrentState().handle_EVcrDependentView(economicEvent_EconomicEvent_StockFlow, ctx);
  }
  @Override
  public void handle_EVcrDecrement(StockFlow object, Context ctx) throws FailedEventHandlingException {

    //The StockFlow object will be saved with its current properties
    //Before changing state and (possibly) attributes, we check there was no change to the masters
    StockFlow currentLedgerObject = (StockFlow)StubHelper.findBusinessObject(ctx, object.getId());
    if(!object.getEconomicResourceId_EconomicResource_StockFlow().equals(currentLedgerObject.getEconomicResourceId_EconomicResource_StockFlow()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getEconomicEventId_EconomicEvent_StockFlow().equals(currentLedgerObject.getEconomicEventId_EconomicEvent_StockFlow()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    //Changes the state of the current StockFlow (object)
    StockFlowTransferState newState = new StockFlowTransferState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //EconomicResource
    EconomicResource economicResource_EconomicResource_StockFlow = (EconomicResource)StubHelper.findBusinessObject(ctx, object.getEconomicResourceId_EconomicResource_StockFlow());
    economicResource_EconomicResource_StockFlow.getCurrentState().handle_EVcrDecrement(economicResource_EconomicResource_StockFlow, ctx);
    //EconomicEvent
    EconomicEvent economicEvent_EconomicEvent_StockFlow = (EconomicEvent)StubHelper.findBusinessObject(ctx, object.getEconomicEventId_EconomicEvent_StockFlow());
    economicEvent_EconomicEvent_StockFlow.getCurrentState().handle_EVcrDecrement(economicEvent_EconomicEvent_StockFlow, ctx);
  }
  @Override
  public void handle_endLastDependenctView(StockFlow object, Context ctx) throws FailedEventHandlingException {

    //The StockFlow object will be saved with its current properties
    //Before changing state and (possibly) attributes, we check there was no change to the masters
    StockFlow currentLedgerObject = (StockFlow)StubHelper.findBusinessObject(ctx, object.getId());
    if(!object.getEconomicResourceId_EconomicResource_StockFlow().equals(currentLedgerObject.getEconomicResourceId_EconomicResource_StockFlow()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getEconomicEventId_EconomicEvent_StockFlow().equals(currentLedgerObject.getEconomicEventId_EconomicEvent_StockFlow()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    //Changes the state of the current StockFlow (object)
    StockFlowInflowState newState = new StockFlowInflowState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //EconomicResource
    EconomicResource economicResource_EconomicResource_StockFlow = (EconomicResource)StubHelper.findBusinessObject(ctx, object.getEconomicResourceId_EconomicResource_StockFlow());
    economicResource_EconomicResource_StockFlow.getCurrentState().handle_endLastDependenctView(economicResource_EconomicResource_StockFlow, ctx);
    //EconomicEvent
    EconomicEvent economicEvent_EconomicEvent_StockFlow = (EconomicEvent)StubHelper.findBusinessObject(ctx, object.getEconomicEventId_EconomicEvent_StockFlow());
    economicEvent_EconomicEvent_StockFlow.getCurrentState().handle_endLastDependenctView(economicEvent_EconomicEvent_StockFlow, ctx);
  }

  //--- Ending Events ---
}

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

public class StockFlowAllocatedState extends StockFlowState {
  //========== Constructor ===========
  public StockFlowAllocatedState() {
    super("allocated", StateType.INITIAL);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  @Override
  public void handle_EVcrStockFlow(StockFlow object, Context ctx) throws FailedEventHandlingException {
    //Check if the referenced masters exist
    //EconomicResource
    if(!StubHelper.exists(ctx, object.getEconomicResourceId_EconomicResource_StockFlow()))
      throw new FailedEventHandlingException("Master with id " + object.getEconomicResourceId_EconomicResource_StockFlow() + " does not exist");

    //Retrieve Master (economicResource_EconomicResource_StockFlow)
    EconomicResource economicResource_EconomicResource_StockFlow = (EconomicResource)StubHelper.findBusinessObject(ctx, object.getEconomicResourceId_EconomicResource_StockFlow());

    //EconomicEvent
    if(!StubHelper.exists(ctx, object.getEconomicEventId_EconomicEvent_StockFlow()))
      throw new FailedEventHandlingException("Master with id " + object.getEconomicEventId_EconomicEvent_StockFlow() + " does not exist");

    //Retrieve Master (economicEvent_EconomicEvent_StockFlow)
    EconomicEvent economicEvent_EconomicEvent_StockFlow = (EconomicEvent)StubHelper.findBusinessObject(ctx, object.getEconomicEventId_EconomicEvent_StockFlow());

    //Master economicEvent can only have one dependent of type StockFlow. Checking if one already exists
    if(StubHelper.hasLivingDependentsOfType(ctx, economicEvent_EconomicEvent_StockFlow, object))
      throw new FailedEventHandlingException("Master with id " + object.getEconomicEventId_EconomicEvent_StockFlow() + " already has a living dependent of type StockFlow (limited to one)");

    //Changes the state of the current StockFlow (object)
    StockFlowUseState newState = new StockFlowUseState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //EconomicResource
    economicResource_EconomicResource_StockFlow.getCurrentState().handle_EVcrStockFlow(economicResource_EconomicResource_StockFlow, ctx);

    //EconomicEvent
    economicEvent_EconomicEvent_StockFlow.getCurrentState().handle_EVcrStockFlow(economicEvent_EconomicEvent_StockFlow, ctx);

  }
  //--- Modifying Events ---

  //--- Ending Events ---
}

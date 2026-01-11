package state.increment;

import entity.Increment;
import entity.Participation;
import entity.Ownership;
import entity.StockFlow;
import entity.EconomicResource;
import entity.Duality;
import entity.EconomicAgent;
import entity.EconomicEvent;

import runtime.StubHelper;
import runtime.BusinessObject;
import runtime.exception.FailedEventHandlingException;
import runtime.exception.BusinessObjectNotFoundException;
import org.bouncycastle.util.encoders.Base64;


import org.hyperledger.fabric.contract.Context;

import java.util.function.Supplier;

public class IncrementExistsState extends IncrementState {
  //========== Constructor ===========
  public IncrementExistsState() {
    super("exists", StateType.ONGOING);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  //--- Modifying Events ---
  @Override
  public void handle_EVendDependentView(Increment object, Context ctx) throws FailedEventHandlingException {

    //The Increment object will be saved with its current properties
    //Before changing state and (possibly) attributes, we check there was no change to the masters
    Increment currentLedgerObject = (Increment)StubHelper.findBusinessObject(ctx, object.getId());
    if(!object.getStockFlowId_StockFlow_Increment().equals(currentLedgerObject.getStockFlowId_StockFlow_Increment()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getDualityId_Duality_Increment().equals(currentLedgerObject.getDualityId_Duality_Increment()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getParticipationId_Participation_Increment().equals(currentLedgerObject.getParticipationId_Participation_Increment()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getOwnershipId_Ownership_Increment().equals(currentLedgerObject.getOwnershipId_Ownership_Increment()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    //Changes the state of the current Increment (object)
    IncrementExistsState newState = new IncrementExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //StockFlow
    StockFlow stockFlow_StockFlow_Increment = (StockFlow)StubHelper.findBusinessObject(ctx, object.getStockFlowId_StockFlow_Increment());
    stockFlow_StockFlow_Increment.getCurrentState().handle_EVendDependentView(stockFlow_StockFlow_Increment, ctx);
    //Duality
    Duality duality_Duality_Increment = (Duality)StubHelper.findBusinessObject(ctx, object.getDualityId_Duality_Increment());
    duality_Duality_Increment.getCurrentState().handle_EVendDependentView(duality_Duality_Increment, ctx);
    //Participation
    Participation participation_Participation_Increment = (Participation)StubHelper.findBusinessObject(ctx, object.getParticipationId_Participation_Increment());
    participation_Participation_Increment.getCurrentState().handle_EVendDependentView(participation_Participation_Increment, ctx);
    //Ownership
    Ownership ownership_Ownership_Increment = (Ownership)StubHelper.findBusinessObject(ctx, object.getOwnershipId_Ownership_Increment());
    ownership_Ownership_Increment.getCurrentState().handle_EVendDependentView(ownership_Ownership_Increment, ctx);
  }
  @Override
  public void handle_EVcrDependentView(Increment object, Context ctx) throws FailedEventHandlingException {

    //The Increment object will be saved with its current properties
    //Before changing state and (possibly) attributes, we check there was no change to the masters
    Increment currentLedgerObject = (Increment)StubHelper.findBusinessObject(ctx, object.getId());
    if(!object.getStockFlowId_StockFlow_Increment().equals(currentLedgerObject.getStockFlowId_StockFlow_Increment()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getDualityId_Duality_Increment().equals(currentLedgerObject.getDualityId_Duality_Increment()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getParticipationId_Participation_Increment().equals(currentLedgerObject.getParticipationId_Participation_Increment()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getOwnershipId_Ownership_Increment().equals(currentLedgerObject.getOwnershipId_Ownership_Increment()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    //Changes the state of the current Increment (object)
    IncrementExistsState newState = new IncrementExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //StockFlow
    StockFlow stockFlow_StockFlow_Increment = (StockFlow)StubHelper.findBusinessObject(ctx, object.getStockFlowId_StockFlow_Increment());
    stockFlow_StockFlow_Increment.getCurrentState().handle_EVcrDependentView(stockFlow_StockFlow_Increment, ctx);
    //Duality
    Duality duality_Duality_Increment = (Duality)StubHelper.findBusinessObject(ctx, object.getDualityId_Duality_Increment());
    duality_Duality_Increment.getCurrentState().handle_EVcrDependentView(duality_Duality_Increment, ctx);
    //Participation
    Participation participation_Participation_Increment = (Participation)StubHelper.findBusinessObject(ctx, object.getParticipationId_Participation_Increment());
    participation_Participation_Increment.getCurrentState().handle_EVcrDependentView(participation_Participation_Increment, ctx);
    //Ownership
    Ownership ownership_Ownership_Increment = (Ownership)StubHelper.findBusinessObject(ctx, object.getOwnershipId_Ownership_Increment());
    ownership_Ownership_Increment.getCurrentState().handle_EVcrDependentView(ownership_Ownership_Increment, ctx);
  }
  @Override
  public void handle_endLastDependenctView(Increment object, Context ctx) throws FailedEventHandlingException {

    //The Increment object will be saved with its current properties
    //Before changing state and (possibly) attributes, we check there was no change to the masters
    Increment currentLedgerObject = (Increment)StubHelper.findBusinessObject(ctx, object.getId());
    if(!object.getStockFlowId_StockFlow_Increment().equals(currentLedgerObject.getStockFlowId_StockFlow_Increment()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getDualityId_Duality_Increment().equals(currentLedgerObject.getDualityId_Duality_Increment()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getParticipationId_Participation_Increment().equals(currentLedgerObject.getParticipationId_Participation_Increment()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getOwnershipId_Ownership_Increment().equals(currentLedgerObject.getOwnershipId_Ownership_Increment()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    //Changes the state of the current Increment (object)
    IncrementExistsState newState = new IncrementExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //StockFlow
    StockFlow stockFlow_StockFlow_Increment = (StockFlow)StubHelper.findBusinessObject(ctx, object.getStockFlowId_StockFlow_Increment());
    stockFlow_StockFlow_Increment.getCurrentState().handle_endLastDependenctView(stockFlow_StockFlow_Increment, ctx);
    //Duality
    Duality duality_Duality_Increment = (Duality)StubHelper.findBusinessObject(ctx, object.getDualityId_Duality_Increment());
    duality_Duality_Increment.getCurrentState().handle_endLastDependenctView(duality_Duality_Increment, ctx);
    //Participation
    Participation participation_Participation_Increment = (Participation)StubHelper.findBusinessObject(ctx, object.getParticipationId_Participation_Increment());
    participation_Participation_Increment.getCurrentState().handle_endLastDependenctView(participation_Participation_Increment, ctx);
    //Ownership
    Ownership ownership_Ownership_Increment = (Ownership)StubHelper.findBusinessObject(ctx, object.getOwnershipId_Ownership_Increment());
    ownership_Ownership_Increment.getCurrentState().handle_endLastDependenctView(ownership_Ownership_Increment, ctx);
  }

  //--- Ending Events ---
  @Override
  public void handle_EVendIncrement(Increment object, Context ctx) throws FailedEventHandlingException {

    //Check if there are living  dependents before ending the object
    if(StubHelper.hasLivingDependents(ctx, object))
      throw new FailedEventHandlingException("Increment (" + object.getId() + ") has living dependents and therefore cannot be ended");

    //Changes the state of the current Increment (object)
    IncrementEndedState newState = new IncrementEndedState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //StockFlow
    StockFlow stockFlow_StockFlow_Increment = (StockFlow)StubHelper.findBusinessObject(ctx, object.getStockFlowId_StockFlow_Increment());
    stockFlow_StockFlow_Increment.getCurrentState().handle_EVendIncrement(stockFlow_StockFlow_Increment, ctx);
    //Duality
    Duality duality_Duality_Increment = (Duality)StubHelper.findBusinessObject(ctx, object.getDualityId_Duality_Increment());
    duality_Duality_Increment.getCurrentState().handle_EVendIncrement(duality_Duality_Increment, ctx);
    //Participation
    Participation participation_Participation_Increment = (Participation)StubHelper.findBusinessObject(ctx, object.getParticipationId_Participation_Increment());
    participation_Participation_Increment.getCurrentState().handle_EVendIncrement(participation_Participation_Increment, ctx);
    //Ownership
    Ownership ownership_Ownership_Increment = (Ownership)StubHelper.findBusinessObject(ctx, object.getOwnershipId_Ownership_Increment());
    ownership_Ownership_Increment.getCurrentState().handle_EVendIncrement(ownership_Ownership_Increment, ctx);
  }
}

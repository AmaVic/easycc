package state.decrement;

import entity.Decrement;
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

public class DecrementExistsState extends DecrementState {
  //========== Constructor ===========
  public DecrementExistsState() {
    super("exists", StateType.ONGOING);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  //--- Modifying Events ---
  @Override
  public void handle_EVendDependentView(Decrement object, Context ctx) throws FailedEventHandlingException {

    //The Decrement object will be saved with its current properties
    //Before changing state and (possibly) attributes, we check there was no change to the masters
    Decrement currentLedgerObject = (Decrement)StubHelper.findBusinessObject(ctx, object.getId());
    if(!object.getStockFlowId_StockFlow_Decrement().equals(currentLedgerObject.getStockFlowId_StockFlow_Decrement()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getDualityId_Duality_Decrement().equals(currentLedgerObject.getDualityId_Duality_Decrement()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getParticipationId_Participation_Decrement().equals(currentLedgerObject.getParticipationId_Participation_Decrement()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getOwnershipId_Ownership_Decrement().equals(currentLedgerObject.getOwnershipId_Ownership_Decrement()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    //Changes the state of the current Decrement (object)
    DecrementExistsState newState = new DecrementExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //StockFlow
    StockFlow stockFlow_StockFlow_Decrement = (StockFlow)StubHelper.findBusinessObject(ctx, object.getStockFlowId_StockFlow_Decrement());
    stockFlow_StockFlow_Decrement.getCurrentState().handle_EVendDependentView(stockFlow_StockFlow_Decrement, ctx);
    //Duality
    Duality duality_Duality_Decrement = (Duality)StubHelper.findBusinessObject(ctx, object.getDualityId_Duality_Decrement());
    duality_Duality_Decrement.getCurrentState().handle_EVendDependentView(duality_Duality_Decrement, ctx);
    //Participation
    Participation participation_Participation_Decrement = (Participation)StubHelper.findBusinessObject(ctx, object.getParticipationId_Participation_Decrement());
    participation_Participation_Decrement.getCurrentState().handle_EVendDependentView(participation_Participation_Decrement, ctx);
    //Ownership
    Ownership ownership_Ownership_Decrement = (Ownership)StubHelper.findBusinessObject(ctx, object.getOwnershipId_Ownership_Decrement());
    ownership_Ownership_Decrement.getCurrentState().handle_EVendDependentView(ownership_Ownership_Decrement, ctx);
  }
  @Override
  public void handle_EVcrDependentView(Decrement object, Context ctx) throws FailedEventHandlingException {

    //The Decrement object will be saved with its current properties
    //Before changing state and (possibly) attributes, we check there was no change to the masters
    Decrement currentLedgerObject = (Decrement)StubHelper.findBusinessObject(ctx, object.getId());
    if(!object.getStockFlowId_StockFlow_Decrement().equals(currentLedgerObject.getStockFlowId_StockFlow_Decrement()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getDualityId_Duality_Decrement().equals(currentLedgerObject.getDualityId_Duality_Decrement()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getParticipationId_Participation_Decrement().equals(currentLedgerObject.getParticipationId_Participation_Decrement()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getOwnershipId_Ownership_Decrement().equals(currentLedgerObject.getOwnershipId_Ownership_Decrement()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    //Changes the state of the current Decrement (object)
    DecrementExistsState newState = new DecrementExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //StockFlow
    StockFlow stockFlow_StockFlow_Decrement = (StockFlow)StubHelper.findBusinessObject(ctx, object.getStockFlowId_StockFlow_Decrement());
    stockFlow_StockFlow_Decrement.getCurrentState().handle_EVcrDependentView(stockFlow_StockFlow_Decrement, ctx);
    //Duality
    Duality duality_Duality_Decrement = (Duality)StubHelper.findBusinessObject(ctx, object.getDualityId_Duality_Decrement());
    duality_Duality_Decrement.getCurrentState().handle_EVcrDependentView(duality_Duality_Decrement, ctx);
    //Participation
    Participation participation_Participation_Decrement = (Participation)StubHelper.findBusinessObject(ctx, object.getParticipationId_Participation_Decrement());
    participation_Participation_Decrement.getCurrentState().handle_EVcrDependentView(participation_Participation_Decrement, ctx);
    //Ownership
    Ownership ownership_Ownership_Decrement = (Ownership)StubHelper.findBusinessObject(ctx, object.getOwnershipId_Ownership_Decrement());
    ownership_Ownership_Decrement.getCurrentState().handle_EVcrDependentView(ownership_Ownership_Decrement, ctx);
  }
  @Override
  public void handle_endLastDependenctView(Decrement object, Context ctx) throws FailedEventHandlingException {

    //The Decrement object will be saved with its current properties
    //Before changing state and (possibly) attributes, we check there was no change to the masters
    Decrement currentLedgerObject = (Decrement)StubHelper.findBusinessObject(ctx, object.getId());
    if(!object.getStockFlowId_StockFlow_Decrement().equals(currentLedgerObject.getStockFlowId_StockFlow_Decrement()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getDualityId_Duality_Decrement().equals(currentLedgerObject.getDualityId_Duality_Decrement()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getParticipationId_Participation_Decrement().equals(currentLedgerObject.getParticipationId_Participation_Decrement()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getOwnershipId_Ownership_Decrement().equals(currentLedgerObject.getOwnershipId_Ownership_Decrement()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    //Changes the state of the current Decrement (object)
    DecrementExistsState newState = new DecrementExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //StockFlow
    StockFlow stockFlow_StockFlow_Decrement = (StockFlow)StubHelper.findBusinessObject(ctx, object.getStockFlowId_StockFlow_Decrement());
    stockFlow_StockFlow_Decrement.getCurrentState().handle_endLastDependenctView(stockFlow_StockFlow_Decrement, ctx);
    //Duality
    Duality duality_Duality_Decrement = (Duality)StubHelper.findBusinessObject(ctx, object.getDualityId_Duality_Decrement());
    duality_Duality_Decrement.getCurrentState().handle_endLastDependenctView(duality_Duality_Decrement, ctx);
    //Participation
    Participation participation_Participation_Decrement = (Participation)StubHelper.findBusinessObject(ctx, object.getParticipationId_Participation_Decrement());
    participation_Participation_Decrement.getCurrentState().handle_endLastDependenctView(participation_Participation_Decrement, ctx);
    //Ownership
    Ownership ownership_Ownership_Decrement = (Ownership)StubHelper.findBusinessObject(ctx, object.getOwnershipId_Ownership_Decrement());
    ownership_Ownership_Decrement.getCurrentState().handle_endLastDependenctView(ownership_Ownership_Decrement, ctx);
  }

  //--- Ending Events ---
  @Override
  public void handle_EVendDecrement(Decrement object, Context ctx) throws FailedEventHandlingException {

    //Check if there are living  dependents before ending the object
    if(StubHelper.hasLivingDependents(ctx, object))
      throw new FailedEventHandlingException("Decrement (" + object.getId() + ") has living dependents and therefore cannot be ended");

    //Changes the state of the current Decrement (object)
    DecrementEndedState newState = new DecrementEndedState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //StockFlow
    StockFlow stockFlow_StockFlow_Decrement = (StockFlow)StubHelper.findBusinessObject(ctx, object.getStockFlowId_StockFlow_Decrement());
    stockFlow_StockFlow_Decrement.getCurrentState().handle_EVendDecrement(stockFlow_StockFlow_Decrement, ctx);
    //Duality
    Duality duality_Duality_Decrement = (Duality)StubHelper.findBusinessObject(ctx, object.getDualityId_Duality_Decrement());
    duality_Duality_Decrement.getCurrentState().handle_EVendDecrement(duality_Duality_Decrement, ctx);
    //Participation
    Participation participation_Participation_Decrement = (Participation)StubHelper.findBusinessObject(ctx, object.getParticipationId_Participation_Decrement());
    participation_Participation_Decrement.getCurrentState().handle_EVendDecrement(participation_Participation_Decrement, ctx);
    //Ownership
    Ownership ownership_Ownership_Decrement = (Ownership)StubHelper.findBusinessObject(ctx, object.getOwnershipId_Ownership_Decrement());
    ownership_Ownership_Decrement.getCurrentState().handle_EVendDecrement(ownership_Ownership_Decrement, ctx);
  }
}

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

public class IncrementAllocatedState extends IncrementState {
  //========== Constructor ===========
  public IncrementAllocatedState() {
    super("allocated", StateType.INITIAL);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  @Override
  public void handle_EVcrIncrement(Increment object, Context ctx) throws FailedEventHandlingException {
    //Check if the referenced masters exist
    //StockFlow
    if(!StubHelper.exists(ctx, object.getStockFlowId_StockFlow_Increment()))
      throw new FailedEventHandlingException("Master with id " + object.getStockFlowId_StockFlow_Increment() + " does not exist");

    //Retrieve Master (stockFlow_StockFlow_Increment)
    StockFlow stockFlow_StockFlow_Increment = (StockFlow)StubHelper.findBusinessObject(ctx, object.getStockFlowId_StockFlow_Increment());

    //Master stockFlow can only have one dependent of type Increment. Checking if one already exists
    if(StubHelper.hasLivingDependentsOfType(ctx, stockFlow_StockFlow_Increment, object))
      throw new FailedEventHandlingException("Master with id " + object.getStockFlowId_StockFlow_Increment() + " already has a living dependent of type Increment (limited to one)");
    //Duality
    if(!StubHelper.exists(ctx, object.getDualityId_Duality_Increment()))
      throw new FailedEventHandlingException("Master with id " + object.getDualityId_Duality_Increment() + " does not exist");

    //Retrieve Master (duality_Duality_Increment)
    Duality duality_Duality_Increment = (Duality)StubHelper.findBusinessObject(ctx, object.getDualityId_Duality_Increment());

    //Participation
    if(!StubHelper.exists(ctx, object.getParticipationId_Participation_Increment()))
      throw new FailedEventHandlingException("Master with id " + object.getParticipationId_Participation_Increment() + " does not exist");

    //Retrieve Master (participation_Participation_Increment)
    Participation participation_Participation_Increment = (Participation)StubHelper.findBusinessObject(ctx, object.getParticipationId_Participation_Increment());

    //Master participation can only have one dependent of type Increment. Checking if one already exists
    if(StubHelper.hasLivingDependentsOfType(ctx, participation_Participation_Increment, object))
      throw new FailedEventHandlingException("Master with id " + object.getParticipationId_Participation_Increment() + " already has a living dependent of type Increment (limited to one)");
    //Ownership
    if(!StubHelper.exists(ctx, object.getOwnershipId_Ownership_Increment()))
      throw new FailedEventHandlingException("Master with id " + object.getOwnershipId_Ownership_Increment() + " does not exist");

    //Retrieve Master (ownership_Ownership_Increment)
    Ownership ownership_Ownership_Increment = (Ownership)StubHelper.findBusinessObject(ctx, object.getOwnershipId_Ownership_Increment());


    //Checking Multiple Propagation Constraints
    //Checking Multiple Propagation Constraint 563
    Supplier<BusinessObject> ultimateMPCObjectEnabledPath563Supplier = () -> {
      //Fetching StockFlow
      StockFlow mpc_stockFlow_StockFlow_Increment = null;
      try {
        mpc_stockFlow_StockFlow_Increment = (StockFlow) StubHelper.findBusinessObject(ctx, object.getStockFlowId_StockFlow_Increment());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicResource
      EconomicResource mpc_economicResource_EconomicResource_StockFlow = null;
      try {
      mpc_economicResource_EconomicResource_StockFlow = (EconomicResource) StubHelper.findBusinessObject(ctx, mpc_stockFlow_StockFlow_Increment.getEconomicResourceId_EconomicResource_StockFlow());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicResource_EconomicResource_StockFlow;
    };
 
    Supplier<BusinessObject> ultimateMPCObjectDisabledPath563Supplier = () -> {
      //Fetching Ownership
      Ownership mpc_ownership_Ownership_Increment = null;
      try {
        mpc_ownership_Ownership_Increment = (Ownership) StubHelper.findBusinessObject(ctx, object.getOwnershipId_Ownership_Increment());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicResource
      EconomicResource mpc_economicResource_EconomicResource_Ownership = null;
      try {
      mpc_economicResource_EconomicResource_Ownership = (EconomicResource) StubHelper.findBusinessObject(ctx, mpc_ownership_Ownership_Increment.getEconomicResourceId_EconomicResource_Ownership());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicResource_EconomicResource_Ownership;
    };
 
    if(!ultimateMPCObjectEnabledPath563Supplier.get().getId().equals(ultimateMPCObjectDisabledPath563Supplier.get().getId()))
      throw new FailedEventHandlingException("One of the multiple propagation constraints is not met");
    //Checking Multiple Propagation Constraint 570
    Supplier<BusinessObject> ultimateMPCObjectEnabledPath570Supplier = () -> {
      //Fetching Participation
      Participation mpc_participation_Participation_Increment = null;
      try {
        mpc_participation_Participation_Increment = (Participation) StubHelper.findBusinessObject(ctx, object.getParticipationId_Participation_Increment());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicAgent
      EconomicAgent mpc_economicAgent_EconomicAgent_Participation = null;
      try {
      mpc_economicAgent_EconomicAgent_Participation = (EconomicAgent) StubHelper.findBusinessObject(ctx, mpc_participation_Participation_Increment.getEconomicAgentId_EconomicAgent_Participation());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicAgent_EconomicAgent_Participation;
    };
 
    Supplier<BusinessObject> ultimateMPCObjectDisabledPath570Supplier = () -> {
      //Fetching Ownership
      Ownership mpc_ownership_Ownership_Increment = null;
      try {
        mpc_ownership_Ownership_Increment = (Ownership) StubHelper.findBusinessObject(ctx, object.getOwnershipId_Ownership_Increment());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicAgent
      EconomicAgent mpc_economicAgent_EconomicAgent_Ownership = null;
      try {
      mpc_economicAgent_EconomicAgent_Ownership = (EconomicAgent) StubHelper.findBusinessObject(ctx, mpc_ownership_Ownership_Increment.getEconomicAgentId_EconomicAgent_Ownership());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicAgent_EconomicAgent_Ownership;
    };
 
    if(!ultimateMPCObjectEnabledPath570Supplier.get().getId().equals(ultimateMPCObjectDisabledPath570Supplier.get().getId()))
      throw new FailedEventHandlingException("One of the multiple propagation constraints is not met");
    //Checking Multiple Propagation Constraint 576
    Supplier<BusinessObject> ultimateMPCObjectEnabledPath576Supplier = () -> {
      //Fetching StockFlow
      StockFlow mpc_stockFlow_StockFlow_Increment = null;
      try {
        mpc_stockFlow_StockFlow_Increment = (StockFlow) StubHelper.findBusinessObject(ctx, object.getStockFlowId_StockFlow_Increment());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicEvent
      EconomicEvent mpc_economicEvent_EconomicEvent_StockFlow = null;
      try {
      mpc_economicEvent_EconomicEvent_StockFlow = (EconomicEvent) StubHelper.findBusinessObject(ctx, mpc_stockFlow_StockFlow_Increment.getEconomicEventId_EconomicEvent_StockFlow());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicEvent_EconomicEvent_StockFlow;
    };
 
    Supplier<BusinessObject> ultimateMPCObjectDisabledPath576Supplier = () -> {
      //Fetching Participation
      Participation mpc_participation_Participation_Increment = null;
      try {
        mpc_participation_Participation_Increment = (Participation) StubHelper.findBusinessObject(ctx, object.getParticipationId_Participation_Increment());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicEvent
      EconomicEvent mpc_economicEvent_EconomicEvent_Participation = null;
      try {
      mpc_economicEvent_EconomicEvent_Participation = (EconomicEvent) StubHelper.findBusinessObject(ctx, mpc_participation_Participation_Increment.getEconomicEventId_EconomicEvent_Participation());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicEvent_EconomicEvent_Participation;
    };
 
    if(!ultimateMPCObjectEnabledPath576Supplier.get().getId().equals(ultimateMPCObjectDisabledPath576Supplier.get().getId()))
      throw new FailedEventHandlingException("One of the multiple propagation constraints is not met");
    
    
    
    
    
    
    
    
    
    
    
    
    //Checking Multiple Propagation Constraint 581
    //20230801 Start of changes for MPC6 implementation: asymmetric fetch replaced by two symmetric ones, one for claims and one for settlements
    /*
    Supplier<BusinessObject> ultimateMPCObjectEnabledPath581Supplier = () -> {
      //Fetching Duality
      Duality mpc_duality_Duality_Increment = null;
      try {
        mpc_duality_Duality_Increment = (Duality) StubHelper.findBusinessObject(ctx, object.getDualityId_Duality_Increment());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicEvent
      EconomicEvent mpc_economicEvent_Settle_Duality = null;
      try {
      mpc_economicEvent_Settle_Duality = (EconomicEvent) StubHelper.findBusinessObject(ctx, mpc_duality_Duality_Increment.getEconomicEventId_Settle_Duality());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicEvent_Settle_Duality;
    };
    */
    Supplier<BusinessObject> ultimateMPCObjectEnabledPath581Supplier_Settle = () -> {
      //Fetching Duality
      Duality mpc_duality_Duality_Increment = null;
      try {
        mpc_duality_Duality_Increment = (Duality) StubHelper.findBusinessObject(ctx, object.getDualityId_Duality_Increment());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicEvent
      EconomicEvent mpc_economicEvent_Settle_Duality = null;
      try {
      mpc_economicEvent_Settle_Duality = (EconomicEvent) StubHelper.findBusinessObject(ctx, mpc_duality_Duality_Increment.getEconomicEventId_Settle_Duality());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicEvent_Settle_Duality;
    };
    
    Supplier<BusinessObject> ultimateMPCObjectEnabledPath581Supplier_Claim = () -> {
      //Fetching Duality
      Duality mpc_duality_Duality_Increment = null;
      try {
        mpc_duality_Duality_Increment = (Duality) StubHelper.findBusinessObject(ctx, object.getDualityId_Duality_Increment());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicEvent
      EconomicEvent mpc_economicEvent_Claim_Duality = null;
      try {
      mpc_economicEvent_Claim_Duality = (EconomicEvent) StubHelper.findBusinessObject(ctx, mpc_duality_Duality_Increment.getEconomicEventId_Claim_Duality());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicEvent_Claim_Duality;
    };
 
    Supplier<BusinessObject> ultimateMPCObjectDisabledPath581Supplier = () -> {
      //Fetching StockFlow
      StockFlow mpc_stockFlow_StockFlow_Increment = null;
      try {
        mpc_stockFlow_StockFlow_Increment = (StockFlow) StubHelper.findBusinessObject(ctx, object.getStockFlowId_StockFlow_Increment());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicEvent
      EconomicEvent mpc_economicEvent_EconomicEvent_StockFlow = null;
      try {
      mpc_economicEvent_EconomicEvent_StockFlow = (EconomicEvent) StubHelper.findBusinessObject(ctx, mpc_stockFlow_StockFlow_Increment.getEconomicEventId_EconomicEvent_StockFlow());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicEvent_EconomicEvent_StockFlow;
    };
 
 	//20230801 asymmetric condition replaced by a symmetric one for claims and settlements 
    //if(!ultimateMPCObjectEnabledPath581Supplier.get().getId().equals(ultimateMPCObjectDisabledPath581Supplier.get().getId()))
    //  throw new FailedEventHandlingException("One of the multiple propagation constraints is not met");
    if(
      ((!ultimateMPCObjectEnabledPath581Supplier_Settle.get().getId().equals(ultimateMPCObjectDisabledPath581Supplier.get().getId()))
    	&& //AND
    	(!ultimateMPCObjectEnabledPath581Supplier_Claim.get().getId().equals(ultimateMPCObjectDisabledPath581Supplier.get().getId())))
      //20230802 add mutual exclusiveness of XOR logic
      || //OR
      ((ultimateMPCObjectEnabledPath581Supplier_Settle.get().getId().equals(ultimateMPCObjectDisabledPath581Supplier.get().getId()))
    	&& //AND
    	(ultimateMPCObjectEnabledPath581Supplier_Claim.get().getId().equals(ultimateMPCObjectDisabledPath581Supplier.get().getId())))
    )
    throw new FailedEventHandlingException("One of the multiple propagation constraints (i.e. 581) is not met");
    //20230801 End of changes for MPC 6 implementation
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //Changes the state of the current Increment (object)
    IncrementExistsState newState = new IncrementExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //StockFlow
    stockFlow_StockFlow_Increment.getCurrentState().handle_EVcrIncrement(stockFlow_StockFlow_Increment, ctx);

    //Duality
    duality_Duality_Increment.getCurrentState().handle_EVcrIncrement(duality_Duality_Increment, ctx);

    //Participation
    participation_Participation_Increment.getCurrentState().handle_EVcrIncrement(participation_Participation_Increment, ctx);

    //Ownership
    ownership_Ownership_Increment.getCurrentState().handle_EVcrIncrement(ownership_Ownership_Increment, ctx);

  }
  //--- Modifying Events ---

  //--- Ending Events ---
}

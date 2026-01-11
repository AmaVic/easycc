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

public class DecrementAllocatedState extends DecrementState {
  //========== Constructor ===========
  public DecrementAllocatedState() {
    super("allocated", StateType.INITIAL);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  @Override
  public void handle_EVcrDecrement(Decrement object, Context ctx) throws FailedEventHandlingException {
    //Check if the referenced masters exist
    //StockFlow
    if(!StubHelper.exists(ctx, object.getStockFlowId_StockFlow_Decrement()))
      throw new FailedEventHandlingException("Master with id " + object.getStockFlowId_StockFlow_Decrement() + " does not exist");

    //Retrieve Master (stockFlow_StockFlow_Decrement)
    StockFlow stockFlow_StockFlow_Decrement = (StockFlow)StubHelper.findBusinessObject(ctx, object.getStockFlowId_StockFlow_Decrement());

    //Master stockFlow can only have one dependent of type Decrement. Checking if one already exists
    if(StubHelper.hasLivingDependentsOfType(ctx, stockFlow_StockFlow_Decrement, object))
      throw new FailedEventHandlingException("Master with id " + object.getStockFlowId_StockFlow_Decrement() + " already has a living dependent of type Decrement (limited to one)");
    //Duality
    if(!StubHelper.exists(ctx, object.getDualityId_Duality_Decrement()))
      throw new FailedEventHandlingException("Master with id " + object.getDualityId_Duality_Decrement() + " does not exist");

    //Retrieve Master (duality_Duality_Decrement)
    Duality duality_Duality_Decrement = (Duality)StubHelper.findBusinessObject(ctx, object.getDualityId_Duality_Decrement());

    //Participation
    if(!StubHelper.exists(ctx, object.getParticipationId_Participation_Decrement()))
      throw new FailedEventHandlingException("Master with id " + object.getParticipationId_Participation_Decrement() + " does not exist");

    //Retrieve Master (participation_Participation_Decrement)
    Participation participation_Participation_Decrement = (Participation)StubHelper.findBusinessObject(ctx, object.getParticipationId_Participation_Decrement());

    //Master participation can only have one dependent of type Decrement. Checking if one already exists
    if(StubHelper.hasLivingDependentsOfType(ctx, participation_Participation_Decrement, object))
      throw new FailedEventHandlingException("Master with id " + object.getParticipationId_Participation_Decrement() + " already has a living dependent of type Decrement (limited to one)");
    //Ownership
    if(!StubHelper.exists(ctx, object.getOwnershipId_Ownership_Decrement()))
      throw new FailedEventHandlingException("Master with id " + object.getOwnershipId_Ownership_Decrement() + " does not exist");

    //Retrieve Master (ownership_Ownership_Decrement)
    Ownership ownership_Ownership_Decrement = (Ownership)StubHelper.findBusinessObject(ctx, object.getOwnershipId_Ownership_Decrement());


    //Checking Multiple Propagation Constraints
    //Checking Multiple Propagation Constraint 678
    Supplier<BusinessObject> ultimateMPCObjectEnabledPath678Supplier = () -> {
      //Fetching StockFlow
      StockFlow mpc_stockFlow_StockFlow_Decrement = null;
      try {
        mpc_stockFlow_StockFlow_Decrement = (StockFlow) StubHelper.findBusinessObject(ctx, object.getStockFlowId_StockFlow_Decrement());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicResource
      EconomicResource mpc_economicResource_EconomicResource_StockFlow = null;
      try {
      mpc_economicResource_EconomicResource_StockFlow = (EconomicResource) StubHelper.findBusinessObject(ctx, mpc_stockFlow_StockFlow_Decrement.getEconomicResourceId_EconomicResource_StockFlow());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicResource_EconomicResource_StockFlow;
    };
 
    Supplier<BusinessObject> ultimateMPCObjectDisabledPath678Supplier = () -> {
      //Fetching Ownership
      Ownership mpc_ownership_Ownership_Decrement = null;
      try {
        mpc_ownership_Ownership_Decrement = (Ownership) StubHelper.findBusinessObject(ctx, object.getOwnershipId_Ownership_Decrement());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicResource
      EconomicResource mpc_economicResource_EconomicResource_Ownership = null;
      try {
      mpc_economicResource_EconomicResource_Ownership = (EconomicResource) StubHelper.findBusinessObject(ctx, mpc_ownership_Ownership_Decrement.getEconomicResourceId_EconomicResource_Ownership());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicResource_EconomicResource_Ownership;
    };
 
    if(!ultimateMPCObjectEnabledPath678Supplier.get().getId().equals(ultimateMPCObjectDisabledPath678Supplier.get().getId()))
      throw new FailedEventHandlingException("One of the multiple propagation constraints is not met");
    //Checking Multiple Propagation Constraint 683
    Supplier<BusinessObject> ultimateMPCObjectEnabledPath683Supplier = () -> {
      //Fetching Participation
      Participation mpc_participation_Participation_Decrement = null;
      try {
        mpc_participation_Participation_Decrement = (Participation) StubHelper.findBusinessObject(ctx, object.getParticipationId_Participation_Decrement());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicAgent
      EconomicAgent mpc_economicAgent_EconomicAgent_Participation = null;
      try {
      mpc_economicAgent_EconomicAgent_Participation = (EconomicAgent) StubHelper.findBusinessObject(ctx, mpc_participation_Participation_Decrement.getEconomicAgentId_EconomicAgent_Participation());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicAgent_EconomicAgent_Participation;
    };
 
    Supplier<BusinessObject> ultimateMPCObjectDisabledPath683Supplier = () -> {
      //Fetching Ownership
      Ownership mpc_ownership_Ownership_Decrement = null;
      try {
        mpc_ownership_Ownership_Decrement = (Ownership) StubHelper.findBusinessObject(ctx, object.getOwnershipId_Ownership_Decrement());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicAgent
      EconomicAgent mpc_economicAgent_EconomicAgent_Ownership = null;
      try {
      mpc_economicAgent_EconomicAgent_Ownership = (EconomicAgent) StubHelper.findBusinessObject(ctx, mpc_ownership_Ownership_Decrement.getEconomicAgentId_EconomicAgent_Ownership());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicAgent_EconomicAgent_Ownership;
    };
 
    if(!ultimateMPCObjectEnabledPath683Supplier.get().getId().equals(ultimateMPCObjectDisabledPath683Supplier.get().getId()))
      throw new FailedEventHandlingException("One of the multiple propagation constraints is not met");
    //Checking Multiple Propagation Constraint 687
    Supplier<BusinessObject> ultimateMPCObjectEnabledPath687Supplier = () -> {
      //Fetching StockFlow
      StockFlow mpc_stockFlow_StockFlow_Decrement = null;
      try {
        mpc_stockFlow_StockFlow_Decrement = (StockFlow) StubHelper.findBusinessObject(ctx, object.getStockFlowId_StockFlow_Decrement());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicEvent
      EconomicEvent mpc_economicEvent_EconomicEvent_StockFlow = null;
      try {
      mpc_economicEvent_EconomicEvent_StockFlow = (EconomicEvent) StubHelper.findBusinessObject(ctx, mpc_stockFlow_StockFlow_Decrement.getEconomicEventId_EconomicEvent_StockFlow());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicEvent_EconomicEvent_StockFlow;
    };
 
    Supplier<BusinessObject> ultimateMPCObjectDisabledPath687Supplier = () -> {
      //Fetching Participation
      Participation mpc_participation_Participation_Decrement = null;
      try {
        mpc_participation_Participation_Decrement = (Participation) StubHelper.findBusinessObject(ctx, object.getParticipationId_Participation_Decrement());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicEvent
      EconomicEvent mpc_economicEvent_EconomicEvent_Participation = null;
      try {
      mpc_economicEvent_EconomicEvent_Participation = (EconomicEvent) StubHelper.findBusinessObject(ctx, mpc_participation_Participation_Decrement.getEconomicEventId_EconomicEvent_Participation());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicEvent_EconomicEvent_Participation;
    };
 
    if(!ultimateMPCObjectEnabledPath687Supplier.get().getId().equals(ultimateMPCObjectDisabledPath687Supplier.get().getId()))
      throw new FailedEventHandlingException("One of the multiple propagation constraints is not met");
    
    
    
    
    //Checking Multiple Propagation Constraint 690
    //20230801 Start of changes for MPC6 implementation: asymmetric fetch replaced by two symmetric ones, one for claims and one for settlements
    /*
    Supplier<BusinessObject> ultimateMPCObjectEnabledPath690Supplier = () -> {
      //Fetching Duality
      Duality mpc_duality_Duality_Decrement = null;
      try {
        mpc_duality_Duality_Decrement = (Duality) StubHelper.findBusinessObject(ctx, object.getDualityId_Duality_Decrement());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicEvent
      EconomicEvent mpc_economicEvent_Claim_Duality = null;
      try {
      mpc_economicEvent_Claim_Duality = (EconomicEvent) StubHelper.findBusinessObject(ctx, mpc_duality_Duality_Decrement.getEconomicEventId_Claim_Duality());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicEvent_Claim_Duality;
    };
    */
    Supplier<BusinessObject> ultimateMPCObjectEnabledPath690Supplier_Claim = () -> {
      //Fetching Duality
      Duality mpc_duality_Duality_Decrement = null;
      try {
        mpc_duality_Duality_Decrement = (Duality) StubHelper.findBusinessObject(ctx, object.getDualityId_Duality_Decrement());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicEvent
      EconomicEvent mpc_economicEvent_Claim_Duality = null;
      try {
      mpc_economicEvent_Claim_Duality = (EconomicEvent) StubHelper.findBusinessObject(ctx, mpc_duality_Duality_Decrement.getEconomicEventId_Claim_Duality());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicEvent_Claim_Duality;
    };
        
    Supplier<BusinessObject> ultimateMPCObjectEnabledPath690Supplier_Settle = () -> {
      //Fetching Duality
      Duality mpc_duality_Duality_Decrement = null;
      try {
        mpc_duality_Duality_Decrement = (Duality) StubHelper.findBusinessObject(ctx, object.getDualityId_Duality_Decrement());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicEvent
      EconomicEvent mpc_economicEvent_Settle_Duality = null;
      try {
      mpc_economicEvent_Settle_Duality = (EconomicEvent) StubHelper.findBusinessObject(ctx, mpc_duality_Duality_Decrement.getEconomicEventId_Settle_Duality());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicEvent_Settle_Duality;
    };
 
    Supplier<BusinessObject> ultimateMPCObjectDisabledPath690Supplier = () -> {
      //Fetching StockFlow
      StockFlow mpc_stockFlow_StockFlow_Decrement = null;
      try {
        mpc_stockFlow_StockFlow_Decrement = (StockFlow) StubHelper.findBusinessObject(ctx, object.getStockFlowId_StockFlow_Decrement());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicEvent
      EconomicEvent mpc_economicEvent_EconomicEvent_StockFlow = null;
      try {
      mpc_economicEvent_EconomicEvent_StockFlow = (EconomicEvent) StubHelper.findBusinessObject(ctx, mpc_stockFlow_StockFlow_Decrement.getEconomicEventId_EconomicEvent_StockFlow());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicEvent_EconomicEvent_StockFlow;
    };
	//20230801 asymmetric condition replaced by a symmetric one for claims and settlements 
    //if(!ultimateMPCObjectEnabledPath690Supplier.get().getId().equals(ultimateMPCObjectDisabledPath690Supplier.get().getId()))
    //  throw new FailedEventHandlingException("One of the multiple propagation constraints is not met");
    if(
      ((!ultimateMPCObjectEnabledPath690Supplier_Claim.get().getId().equals(ultimateMPCObjectDisabledPath690Supplier.get().getId())) 
    	&& //AND
    	!ultimateMPCObjectEnabledPath690Supplier_Settle.get().getId().equals(ultimateMPCObjectDisabledPath690Supplier.get().getId()))
      //20230802 add mutual exclusiveness of XOR logic
      || //OR
      ((ultimateMPCObjectEnabledPath690Supplier_Claim.get().getId().equals(ultimateMPCObjectDisabledPath690Supplier.get().getId())) 
    	&& //AND
    	ultimateMPCObjectEnabledPath690Supplier_Settle.get().getId().equals(ultimateMPCObjectDisabledPath690Supplier.get().getId()))
      )
    throw new FailedEventHandlingException("One of the multiple propagation constraints (i.e. 690) is not met");
    //20230801 End of changes for MPC 6 implementation
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //Changes the state of the current Decrement (object)
    DecrementExistsState newState = new DecrementExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //StockFlow
    stockFlow_StockFlow_Decrement.getCurrentState().handle_EVcrDecrement(stockFlow_StockFlow_Decrement, ctx);

    //Duality
    duality_Duality_Decrement.getCurrentState().handle_EVcrDecrement(duality_Duality_Decrement, ctx);

    //Participation
    participation_Participation_Decrement.getCurrentState().handle_EVcrDecrement(participation_Participation_Decrement, ctx);

    //Ownership
    ownership_Ownership_Decrement.getCurrentState().handle_EVcrDecrement(ownership_Ownership_Decrement, ctx);

  }
  //--- Modifying Events ---

  //--- Ending Events ---
}

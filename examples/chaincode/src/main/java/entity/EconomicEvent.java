package entity;

import java.time.LocalDateTime;

import com.owlike.genson.annotation.JsonCreator;
import com.owlike.genson.annotation.JsonProperty;

import runtime.BusinessObject;

import state.economicEvent.EconomicEventState;
import state.economicEvent.EconomicEventAllocatedState;

public class EconomicEvent extends BusinessObject {
   //=========== Attributes ===========
  private final boolean isParticipant = false;
  private EconomicEventState currentState;
  private LocalDateTime TimeStamp;
  private Integer CountDependentViews;
  private String Name;

  //========== Constructors ==========
  /**
   * Creates a new EconomicEvent with a Generated ID and Default State: Allocated
   */
  @JsonCreator
  //public EconomicEvent(@JsonProperty("TimeStamp") LocalDateTime TimeStamp, @JsonProperty("CountDependentViews") Integer CountDependentViews, @JsonProperty("Name") String Name) { // original generated code
  //20230801 modified according to Victor's instructions:
  /**
   * Manual Change (@Wim)
   * This constructor is used to create new economic events, so in this one we can remove the CountDependentViews parameter, and set the default value to 0.
   */
  public EconomicEvent(@JsonProperty("TimeStamp") LocalDateTime TimeStamp, @JsonProperty("Name") String Name) {
    super();
    this.currentState = new EconomicEventAllocatedState();
    this.TimeStamp = TimeStamp;
    //this.CountDependentViews = CountDependentViews; // original generated code
    //20221012 & 20230801 modified by Wim Laurier, reproducing Monique Snoecks code modifications for the JIS paper: 
    //Initiate CountDependentViews at zero regardless of the user input
    this.CountDependentViews = 0;//manual modification
    this.Name = Name;
  }

  //20230801: No modification according to Victor's instructions:
  /**
   * @Wim: this constructor is used to instanciate already existing objects, so we need to leave the CountDepdendentViews as parameter
   */
  public EconomicEvent(@JsonProperty("id") String id, @JsonProperty("currentState") EconomicEventState currentState, @JsonProperty("TimeStamp") LocalDateTime TimeStamp, @JsonProperty("CountDependentViews") Integer CountDependentViews, @JsonProperty("Name") String Name) {
    super(id);
    this.currentState = currentState;
    this.TimeStamp = TimeStamp;
    this.CountDependentViews = CountDependentViews;
    this.Name = Name;
  }

  //============ Getters =============
  @Override
  public boolean isParticipant() { return this.isParticipant; }
  @Override
  public String getPublicKey() { return null; }
  public EconomicEventState getCurrentState() { return this.currentState; }
  public LocalDateTime getTimeStamp() { return this.TimeStamp; }
  public Integer getCountDependentViews() { return this.CountDependentViews; }
  public String getName() { return this.Name; }

  //============ Setters =============
  public void setCurrentState(EconomicEventState currentState) { this.currentState = currentState; }
  public void setTimeStamp(LocalDateTime TimeStamp) { this.TimeStamp = TimeStamp; }
  public void setCountDependentViews(Integer CountDependentViews) { this.CountDependentViews = CountDependentViews; }
  public void setName(String Name) { this.Name = Name; }

  //===== JSON (De)serialization =====
  public String toJsonString() { return genson.serialize(this); }
  public static EconomicEvent fromJson(String json) { return genson.deserialize(json, EconomicEvent.class); }

  //20230802 Manual Modification to allow for uniqueness checking
  public String getUniquenessQuery(){
    String query = "{\"selector\":{\"shouldNotYieldAResult\":\"shouldNotYieldAResult\"}}";
    return query;
  }
}
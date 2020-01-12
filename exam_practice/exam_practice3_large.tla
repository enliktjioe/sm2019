------------------------ MODULE exam_practice3_large ------------------------
EXTENDS Integers
 
CONSTANT Elevators
CONSTANT NumFloors
 
 
(* --algorithm elevator
 
variables
    loc = [e \in Elevators |-> 1],
    dir = [e \in Elevators |-> "UP"],
    idle = Elevators,
    req = {}

define
    Floors == 1..NumFloors
    current_floors == { loc[e] : e \in Elevators }
end define

fair process requesting = "req"
begin Press:
    with b \in Floors do
        if b \notin current_floors then
            req := req \cup {b}
        end if
    end with;
    goto Press
end process

fair process elevator \in Elevators
begin
    Check:
    if loc[self] \in req then
        Open:
        req := req \ {loc[self]};
        idle := idle \cup {self}
    end if;
    Move:
    await self \notin idle;
    if dir[self] = "UP" then
        loc[self] := loc[self] + 1
    else
        loc[self] := loc[self] - 1
    end if;
    goto Check
end process

fair process controller = "ctrl"
begin
    Controller:
    await req /= {} /\ idle /= {};
    with e \in idle do
        if \E f \in req : f < loc[e] then
            dir[e] := "DOWN"
        elsif \E f \in req : f > loc[e] then
            dir[e] := "UP"
        end if;
        idle := idle \ {e}
    end with;
    goto Controller
end process
 
end algorithm *)
\* BEGIN TRANSLATION
VARIABLES loc, dir, idle, req, pc

(* define statement *)
Floors == 1..NumFloors
current_floors == { loc[e] : e \in Elevators }


vars == << loc, dir, idle, req, pc >>

ProcSet == {"req"} \cup (Elevators) \cup {"ctrl"}

Init == (* Global variables *)
        /\ loc = [e \in Elevators |-> 1]
        /\ dir = [e \in Elevators |-> "UP"]
        /\ idle = Elevators
        /\ req = {}
        /\ pc = [self \in ProcSet |-> CASE self = "req" -> "Press"
                                        [] self \in Elevators -> "Check"
                                        [] self = "ctrl" -> "Controller"]

Press == /\ pc["req"] = "Press"
         /\ \E b \in Floors:
              IF b \notin current_floors
                 THEN /\ req' = (req \cup {b})
                 ELSE /\ TRUE
                      /\ req' = req
         /\ pc' = [pc EXCEPT !["req"] = "Press"]
         /\ UNCHANGED << loc, dir, idle >>

requesting == Press

Check(self) == /\ pc[self] = "Check"
               /\ IF loc[self] \in req
                     THEN /\ pc' = [pc EXCEPT ![self] = "Open"]
                     ELSE /\ pc' = [pc EXCEPT ![self] = "Move"]
               /\ UNCHANGED << loc, dir, idle, req >>

Open(self) == /\ pc[self] = "Open"
              /\ req' = req \ {loc[self]}
              /\ idle' = (idle \cup {self})
              /\ pc' = [pc EXCEPT ![self] = "Move"]
              /\ UNCHANGED << loc, dir >>

Move(self) == /\ pc[self] = "Move"
              /\ self \notin idle
              /\ IF dir[self] = "UP"
                    THEN /\ loc' = [loc EXCEPT ![self] = loc[self] + 1]
                    ELSE /\ loc' = [loc EXCEPT ![self] = loc[self] - 1]
              /\ pc' = [pc EXCEPT ![self] = "Check"]
              /\ UNCHANGED << dir, idle, req >>

elevator(self) == Check(self) \/ Open(self) \/ Move(self)

Controller == /\ pc["ctrl"] = "Controller"
              /\ req /= {} /\ idle /= {}
              /\ \E e \in idle:
                   /\ IF \E f \in req : f < loc[e]
                         THEN /\ dir' = [dir EXCEPT ![e] = "DOWN"]
                         ELSE /\ IF \E f \in req : f > loc[e]
                                    THEN /\ dir' = [dir EXCEPT ![e] = "UP"]
                                    ELSE /\ TRUE
                                         /\ dir' = dir
                   /\ idle' = idle \ {e}
              /\ pc' = [pc EXCEPT !["ctrl"] = "Controller"]
              /\ UNCHANGED << loc, req >>

controller == Controller

(* Allow infinite stuttering to prevent deadlock on termination. *)
Terminating == /\ \A self \in ProcSet: pc[self] = "Done"
               /\ UNCHANGED vars

Next == requesting \/ controller
           \/ (\E self \in Elevators: elevator(self))
           \/ Terminating

Spec == /\ Init /\ [][Next]_vars
        /\ WF_vars(requesting)
        /\ \A self \in Elevators : WF_vars(elevator(self))
        /\ WF_vars(controller)

Termination == <>(\A self \in ProcSet: pc[self] = "Done")

\* END TRANSLATION

=============================================================================
\* Modification History
\* Last modified Sun Jan 12 11:58:20 EET 2020 by enlik
\* Created Sun Jan 12 11:50:53 EET 2020 by enlik

-------------------------------- MODULE exam_practice --------------------------------
EXTENDS Integers
CONSTANT Threads

(* --algorithm cyclic

variables x = 0

fair+ process cycle \in Threads
begin
    Inc:
    while x < 5 do
        x := x + 1
    end while;
    
    Dec:
    while x > 0 do
        x := x - 1
    end while;
    
    goto Inc
end process


end algorithm *)

\* BEGIN TRANSLATION
VARIABLES x, pc

vars == << x, pc >>

ProcSet == (Threads)

Init == (* Global variables *)
        /\ x = 0
        /\ pc = [self \in ProcSet |-> "Inc"]

Inc(self) == /\ pc[self] = "Inc"
             /\ IF x < 5
                   THEN /\ x' = x + 1
                        /\ pc' = [pc EXCEPT ![self] = "Inc"]
                   ELSE /\ pc' = [pc EXCEPT ![self] = "Dec"]
                        /\ x' = x

Dec(self) == /\ pc[self] = "Dec"
             /\ IF x > 0
                   THEN /\ x' = x - 1
                        /\ pc' = [pc EXCEPT ![self] = "Dec"]
                   ELSE /\ pc' = [pc EXCEPT ![self] = "Inc"]
                        /\ x' = x

cycle(self) == Inc(self) \/ Dec(self)

(* Allow infinite stuttering to prevent deadlock on termination. *)
Terminating == /\ \A self \in ProcSet: pc[self] = "Done"
               /\ UNCHANGED vars

Next == (\E self \in Threads: cycle(self))
           \/ Terminating

Spec == /\ Init /\ [][Next]_vars
        /\ \A self \in Threads : SF_vars(cycle(self))

Termination == <>(\A self \in ProcSet: pc[self] = "Done")

\* END TRANSLATION
=============================================================================
\* Modification History
\* Last modified Mon Jan 13 20:54:47 EET 2020 by enlik
\* Created Sun Jan 12 11:37:03 EET 2020 by enlik

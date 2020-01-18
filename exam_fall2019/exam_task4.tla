----------------------------- MODULE exam_task4 -----------------------------
EXTENDS Integers

(* --algorithm examquiz

variables x = 0

fair+ process inc = "inc"
begin
    Check:
        when x < 2;
    Set:
        x := x + 1;
        goto Check;
end process

fair+ process dec = "dec"
begin
    Check:
        when x > 0;
    Set:
        x := x - 1;
        goto Check
end process

end algorithm *)

\* BEGIN TRANSLATION
\* Label Check of process inc at line 11 col 9 changed to Check_
\* Label Set of process inc at line 13 col 9 changed to Set_
VARIABLES x, pc

vars == << x, pc >>

ProcSet == {"inc"} \cup {"dec"}

Init == (* Global variables *)
        /\ x = 0
        /\ pc = [self \in ProcSet |-> CASE self = "inc" -> "Check_"
                                        [] self = "dec" -> "Check"]

Check_ == /\ pc["inc"] = "Check_"
          /\ x < 2
          /\ pc' = [pc EXCEPT !["inc"] = "Set_"]
          /\ x' = x

Set_ == /\ pc["inc"] = "Set_"
        /\ x' = x + 1
        /\ pc' = [pc EXCEPT !["inc"] = "Check_"]

inc == Check_ \/ Set_

Check == /\ pc["dec"] = "Check"
         /\ x > 0
         /\ pc' = [pc EXCEPT !["dec"] = "Set"]
         /\ x' = x

Set == /\ pc["dec"] = "Set"
       /\ x' = x - 1
       /\ pc' = [pc EXCEPT !["dec"] = "Check"]

dec == Check \/ Set

(* Allow infinite stuttering to prevent deadlock on termination. *)
Terminating == /\ \A self \in ProcSet: pc[self] = "Done"
               /\ UNCHANGED vars

Next == inc \/ dec
           \/ Terminating

Spec == /\ Init /\ [][Next]_vars
        /\ SF_vars(inc)
        /\ SF_vars(dec)

Termination == <>(\A self \in ProcSet: pc[self] = "Done")

\* END TRANSLATION

=============================================================================
\* Modification History
\* Last modified Tue Jan 14 15:12:25 EET 2020 by enlik
\* Created Tue Jan 14 15:09:28 EET 2020 by enlik

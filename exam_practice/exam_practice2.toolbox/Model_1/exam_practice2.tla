--------------------------- MODULE exam_practice2 ---------------------------
EXTENDS Integers
CONSTANT Threads

(* --algorithm inc

variables x = 0

fair+ process updown = "updown"
begin Decide:
    while TRUE do
    either
        when x < 1;
        Inc:
        x := x + 1;
    or
        when x > -1;
        Dec:
        x := x - 1;
    end either;
    end while
end process

fair+ process inc = "inc"
begin
    Check:
        await x = 0;
        x := x + 1;
    goto Check
end process

end algorithm *)


\* BEGIN TRANSLATION
VARIABLES x, pc

vars == << x, pc >>

ProcSet == {"updown"} \cup {"inc"}

Init == (* Global variables *)
        /\ x = 0
        /\ pc = [self \in ProcSet |-> CASE self = "updown" -> "Decide"
                                        [] self = "inc" -> "Check"]

Decide == /\ pc["updown"] = "Decide"
          /\ \/ /\ x < 1
                /\ pc' = [pc EXCEPT !["updown"] = "Inc"]
             \/ /\ x > -1
                /\ pc' = [pc EXCEPT !["updown"] = "Dec"]
          /\ x' = x

Inc == /\ pc["updown"] = "Inc"
       /\ x' = x + 1
       /\ pc' = [pc EXCEPT !["updown"] = "Decide"]

Dec == /\ pc["updown"] = "Dec"
       /\ x' = x - 1
       /\ pc' = [pc EXCEPT !["updown"] = "Decide"]

updown == Decide \/ Inc \/ Dec

Check == /\ pc["inc"] = "Check"
         /\ x = 0
         /\ x' = x + 1
         /\ pc' = [pc EXCEPT !["inc"] = "Check"]

inc == Check

Next == updown \/ inc

Spec == /\ Init /\ [][Next]_vars
        /\ SF_vars(updown)
        /\ SF_vars(inc)

\* END TRANSLATION

=============================================================================
\* Modification History
\* Last modified Sun Jan 12 11:50:17 EET 2020 by enlik
\* Created Sun Jan 12 11:44:48 EET 2020 by enlik

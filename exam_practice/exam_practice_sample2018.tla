---------------------- MODULE exam_practice_sample2018 ----------------------

EXTENDS Integers
CONSTANT Users, Seats

(* --algorithm cinema

variables
    ticket = [s \in Seats |-> F],
    sold = [u \in Users |-> {}]
    
define
    F == "free"
    B == "booked"
    P == "purchased"
    status == {F, B, P}
    
    free_seats == {s \in Seats : ticket[s] = F}
    booked_by == [u \in Users |-> {s \in Seats : ticket[s] = u}]
    soldout == UNION {sold[u] : u \in Users} = Seats
    consistent == \A u1, u2 \in Users : sold[u1] \cap sold[u2] /= {} => u1 = u2
end define

fair+ process user \in Users
variable select = 0
begin
Decide:
    either
        Book:
        if free_seats /= {} then
            with seat \in free_seats do
                select := seat
            end with;
            BookTicket: ticket[select] := self;
        end if;
    or
        Unbook:
        if booked_by[self] /= {} then
            with seat \in booked_by[self] do
                select := seat
            end with;
            ReleaseTicket: ticket[select] := F;
        end if
    end either;

ProceedOrNot:
    either goto Decide or goto Checkout end either;
    
Checkout:
    either
        Purchase:
        ticket := [s \in Seats |-> IF s \in booked_by[self] THEN P ELSE ticket[s]];
        sold[self] := sold[self] \cup booked_by[self]
    or
        Cancel:
        ticket := [s \in Seats |-> IF ticket[s] = self THEN F ELSE ticket[s]];
    end either;
    
NextCustomer:
    goto Decide
    
end process

end algorithm *)
\* BEGIN TRANSLATION
VARIABLES ticket, sold, pc

(* define statement *)
F == "free"
B == "booked"
P == "purchased"
status == {F, B, P}

free_seats == {s \in Seats : ticket[s] = F}
booked_by == [u \in Users |-> {s \in Seats : ticket[s] = u}]
soldout == UNION {sold[u] : u \in Users} = Seats
consistent == \A u1, u2 \in Users : sold[u1] \cap sold[u2] /= {} => u1 = u2

VARIABLE select

vars == << ticket, sold, pc, select >>

ProcSet == (Users)

Init == (* Global variables *)
        /\ ticket = [s \in Seats |-> F]
        /\ sold = [u \in Users |-> {}]
        (* Process user *)
        /\ select = [self \in Users |-> 0]
        /\ pc = [self \in ProcSet |-> "Decide"]

Decide(self) == /\ pc[self] = "Decide"
                /\ \/ /\ pc' = [pc EXCEPT ![self] = "Book"]
                   \/ /\ pc' = [pc EXCEPT ![self] = "Unbook"]
                /\ UNCHANGED << ticket, sold, select >>

Book(self) == /\ pc[self] = "Book"
              /\ IF free_seats /= {}
                    THEN /\ \E seat \in free_seats:
                              select' = [select EXCEPT ![self] = seat]
                         /\ pc' = [pc EXCEPT ![self] = "BookTicket"]
                    ELSE /\ pc' = [pc EXCEPT ![self] = "ProceedOrNot"]
                         /\ UNCHANGED select
              /\ UNCHANGED << ticket, sold >>

BookTicket(self) == /\ pc[self] = "BookTicket"
                    /\ ticket' = [ticket EXCEPT ![select[self]] = self]
                    /\ pc' = [pc EXCEPT ![self] = "ProceedOrNot"]
                    /\ UNCHANGED << sold, select >>

Unbook(self) == /\ pc[self] = "Unbook"
                /\ IF booked_by[self] /= {}
                      THEN /\ \E seat \in booked_by[self]:
                                select' = [select EXCEPT ![self] = seat]
                           /\ pc' = [pc EXCEPT ![self] = "ReleaseTicket"]
                      ELSE /\ pc' = [pc EXCEPT ![self] = "ProceedOrNot"]
                           /\ UNCHANGED select
                /\ UNCHANGED << ticket, sold >>

ReleaseTicket(self) == /\ pc[self] = "ReleaseTicket"
                       /\ ticket' = [ticket EXCEPT ![select[self]] = F]
                       /\ pc' = [pc EXCEPT ![self] = "ProceedOrNot"]
                       /\ UNCHANGED << sold, select >>

ProceedOrNot(self) == /\ pc[self] = "ProceedOrNot"
                      /\ \/ /\ pc' = [pc EXCEPT ![self] = "Decide"]
                         \/ /\ pc' = [pc EXCEPT ![self] = "Checkout"]
                      /\ UNCHANGED << ticket, sold, select >>

Checkout(self) == /\ pc[self] = "Checkout"
                  /\ \/ /\ pc' = [pc EXCEPT ![self] = "Purchase"]
                     \/ /\ pc' = [pc EXCEPT ![self] = "Cancel"]
                  /\ UNCHANGED << ticket, sold, select >>

Purchase(self) == /\ pc[self] = "Purchase"
                  /\ ticket' = [s \in Seats |-> IF s \in booked_by[self] THEN P ELSE ticket[s]]
                  /\ sold' = [sold EXCEPT ![self] = sold[self] \cup booked_by[self]]
                  /\ pc' = [pc EXCEPT ![self] = "NextCustomer"]
                  /\ UNCHANGED select

Cancel(self) == /\ pc[self] = "Cancel"
                /\ ticket' = [s \in Seats |-> IF ticket[s] = self THEN F ELSE ticket[s]]
                /\ pc' = [pc EXCEPT ![self] = "NextCustomer"]
                /\ UNCHANGED << sold, select >>

NextCustomer(self) == /\ pc[self] = "NextCustomer"
                      /\ pc' = [pc EXCEPT ![self] = "Decide"]
                      /\ UNCHANGED << ticket, sold, select >>

user(self) == Decide(self) \/ Book(self) \/ BookTicket(self)
                 \/ Unbook(self) \/ ReleaseTicket(self)
                 \/ ProceedOrNot(self) \/ Checkout(self) \/ Purchase(self)
                 \/ Cancel(self) \/ NextCustomer(self)

(* Allow infinite stuttering to prevent deadlock on termination. *)
Terminating == /\ \A self \in ProcSet: pc[self] = "Done"
               /\ UNCHANGED vars

Next == (\E self \in Users: user(self))
           \/ Terminating

Spec == /\ Init /\ [][Next]_vars
        /\ \A self \in Users : SF_vars(user(self))

Termination == <>(\A self \in ProcSet: pc[self] = "Done")

\* END TRANSLATION

=============================================================================
\* Modification History
\* Last modified Mon Jan 13 13:43:17 EET 2020 by enlik
\* Created Mon Jan 13 13:29:37 EET 2020 by enlik

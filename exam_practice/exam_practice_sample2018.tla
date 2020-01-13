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

=============================================================================
\* Modification History
\* Last modified Mon Jan 13 13:37:35 EET 2020 by enlik
\* Created Mon Jan 13 13:29:37 EET 2020 by enlik

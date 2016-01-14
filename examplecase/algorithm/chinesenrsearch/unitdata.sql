select unit.ID, org.ShortName + org.FullName + unit.Name as unitname 
From Unit
inner join org on unit.OrgID = org.ID
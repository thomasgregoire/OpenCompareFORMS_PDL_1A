
function surligne(champ, erreur)
{
    if(erreur)
        champ.style.backgroundColor = "#d9534f";
    else
        champ.style.backgroundColor = "#5cb85c";
}

function verifMail(champ)
{
    var regex = /^[a-zA-Z0-9._-]+@[a-z0-9._-]{2,}\.[a-z]{2,4}$/;

    if(!regex.test(champ.value))
    {
        surligne(champ, true);
        return false;
    }
    else
    {
        surligne(champ, false);
        return true;
    }
}

function verifPseudo(champ)
{
    if(champ.value.length < 2 || champ.value.length > 25)
    {
        surligne(champ, true);
        return false;
    }
    else
    {
        surligne(champ, false);
        return true;
    }
}

function verifDate(champ)
{
    var regex = /^[0-9]{4}-[0-9]{2}-[0-9]{2}$/;
    if(!regex.test(champ.value))
    {
        surligne(champ, true);
        console.log(champ.value);
        return false;

    }
    else
    {
        surligne(champ, false);
        return true;
    }

}

function verifEntier(value){
    if(!((parseFloat(value.value) == parseInt(value.value)) && !isNaN(value.value))){
        surligne(value, true);

        return true;

    } else {
        surligne(value, false);
        return false;
    }
}

function verifReel(value){
    var regex = /^[0-9]*[.|,]?[0-9]$/;
    if((!regex.test(value.value))){
        surligne(value, true);

        return true;

    } else {
        surligne(value, false);
        return false;
    }
}

function checkboxF(value){
    console.log(value);
}

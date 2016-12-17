/**
 * Created by camille on 15/12/2016.
 */

function afficher_cacher(element1 , element2) {

    console.log("J'ai cliqué");

    var obj = document.getElementById(element1);
    var obj2 = document.getElementById(element2);

    console.log(obj);
    console.log(obj2);

    obj.style.display = (obj.style.display == 'none' ? '' : 'none');
    obj2.style.display = (obj2.style.display == 'none' ? '' : 'none');
}

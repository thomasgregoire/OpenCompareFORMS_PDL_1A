/**
 * Created by camille on 15/12/2016.
 */

function afficher_cacher() {
    var obj = document.getElementById('bouton1');
    var obj2 = document.getElementById('bouton2');

    obj.style.display = (obj.style.display == 'none' ? '' : 'none');
    obj2.style.display = (obj2.style.display == 'none' ? '' : 'none');
}
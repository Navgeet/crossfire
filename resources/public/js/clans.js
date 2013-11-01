function handleDragStart(o, e) {
    o.style.opacity = '0.4';  // this / e.target is the source node.
    e.dataTransfer.effectAllowed = 'move';
    e.dataTransfer.setData('text/html', o.innerHTML);
}

function handleDragEnd(o, e) {
    e.target.parentNode.removeChild(o);
}

function handleDragOver(o, e) {
    if (e.preventDefault) {
        e.preventDefault(); // Necessary. Allows us to drop.
    }

    e.dataTransfer.dropEffect = 'move';  // See the section on the DataTransfer object.

    return false;
}

function handleDrop(o, e) {
    if (e.stopPropagation) {
        e.stopPropagation(); // stops the browser from redirecting.
    }
    // var el = document.querySelector(".myclass");
    var div = document.createElement('div');
    div.innerHTML = "<span draggable=\"true\" ondragend=\"handleDragEnd(this, event)\" ondragstart=\"handleDragStart(this, event)\" class=\"label label-primary\">" + e.dataTransfer.getData('text/html') + "</span>";
    e.target.appendChild(div);
    // o.appendChild(div);
    return false;
}

function add_elem_and_remove(button, e) {
    button.parentNode.removeChild(button);
    e.value = button.innerHTML;
}

function add_elem(button, e) {
    e.value = button.innerHTML;
}

function clear(id) {
    console.log(id);
    document.getElementById("pl1").value = "foo";
}

function button_press(o) {
    console.log(o.innerHTML);

    if(!document.getElementById("pl1").value) return add_elem_and_remove(o, document.getElementById("pl1"));
    if(!document.getElementById("pl2").value) return add_elem_and_remove(o, document.getElementById("pl2"));
    if(!document.getElementById("pl3").value) return add_elem_and_remove(o, document.getElementById("pl3"));
    if(!document.getElementById("pl4").value) return add_elem_and_remove(o, document.getElementById("pl4"));
    if(!document.getElementById("pl5").value) return add_elem_and_remove(o, document.getElementById("pl5"));
    if(!document.getElementById("pl6").value) return add_elem(o, document.getElementById("pl6"));
    return false;
}

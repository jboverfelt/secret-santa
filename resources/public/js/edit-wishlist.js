var opts = {
  theme: {
    base: 'css/epiceditor.css', 
    preview: 'css/github.css', 
    editor: 'css/epic-dark.css'
  }, 
  basePath: '/',
  focusOnLoad: true,
  button: {
    fullscreen: false
  }
}

var editor = new EpicEditor(opts).load();

$.get("/wishlist/json", function(data) {
  editor.importFile('my-wishlist', data.text);
});

$("#wishlist-submit").on("click", function() {
  $("#text-hidden").val(editor.exportFile());
  $("#wishlist-submit").submit();
});

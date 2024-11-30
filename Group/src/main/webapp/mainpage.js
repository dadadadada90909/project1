// Get references to DOM elements
const fileUpload = document.getElementById('fileUpload');
const searchInput = document.getElementById('searchInput');
const previewContainer = document.getElementById('previewContainer');

let uploadedFiles = [];

// Handle file uploads
fileUpload.addEventListener('change', (event) => {
    const files = event.target.files;
    for (let file of files) {
        // Create file preview
        const fileReader = new FileReader();
        fileReader.onload = function(e) {
            const filePreview = createFilePreview(file, e.target.result);
            previewContainer.appendChild(filePreview);
            uploadedFiles.push({ name: file.name, preview: e.target.result });
        };
        fileReader.readAsDataURL(file);
    }
});

// Create a preview for uploaded file
function createFilePreview(file, fileData) {
    const fileType = file.type.split('/')[0]; // Get the type (image, video, etc.)

    const previewDiv = document.createElement('div');
    previewDiv.classList.add('preview-item');
    previewDiv.setAttribute('data-name', file.name);

    let previewContent = '';

    if (fileType === 'image') {
        previewContent = `<img src="${fileData}" alt="${file.name}">`;
    } else if (fileType === 'video') {
        previewContent = `<video controls><source src="${fileData}" type="${file.type}"></video>`;
    } else {
        previewContent = `<p>File: ${file.name}</p>`;
    }

    previewDiv.innerHTML = `${previewContent}<h4>${file.name}</h4>`;
    return previewDiv;
}

// Search files based on input
searchInput.addEventListener('input', (event) => {
    const searchTerm = event.target.value.toLowerCase();

    // Filter uploaded files based on the search term
    const filteredFiles = uploadedFiles.filter(file =>
        file.name.toLowerCase().includes(searchTerm)
    );

    // Clear previous preview and display filtered files
    previewContainer.innerHTML = '';
    filteredFiles.forEach(file => {
        const filePreview = createFilePreview({ name: file.name, type: 'image/jpeg' }, file.preview);
        previewContainer.appendChild(filePreview);
    });
});

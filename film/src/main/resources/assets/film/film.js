const tBody = document.getElementById('tBody');
const ePagination = document.getElementById('pagination')
const eHeaderPublishDate = document.getElementById('header-publish-date')
const eSearch = document.getElementById('search')
const form = document.getElementById('filmForm');

let filmSelected = {};
let pageable = {
    page: 1,
    sort: 'id,desc',
    search: ''
}
$(document).ready(function () {
    $('.js-example-basic-single').select2({
        dropdownParent: $('#staticBackdrop')
    });
    $('.js-example-basic-multiple').select2({
        dropdownParent: $('#staticBackdrop')
    })
});



function renderItemStr(item) {
    return `<tr>
                    <td>
                        ${item.id}
                    </td>
                    <td>
                        ${item.name}
                    </td>
                    <td>
                        ${item.director}
                    </td>
                    <td>
                        ${item.actors}
                    </td>
                    <td>
                        ${item.categories}
                    </td>
                    <td>
                        ${item.publishDate}
                    </td>
                    <td>
                        <a class="btn edit" data-id="${item.id}"><i class="fa-regular fa-pen-to-square text-primary"></i></a>
                        <a class="btn delete" data-id="${item.id}"><i class="fa-regular fa-trash-can text-danger"></i></a>
                    </td>
                </tr>`
}

function renderTBody(items) {
    let str = '';
    items.forEach(e => {
        str += renderItemStr(e);
    })
    tBody.innerHTML = str;
}

async function getList() {
    const response = await fetch(`/api/films?page=${pageable.page - 1 || 0}&sort=${pageable.sortCustom || 'id,desc'}&search=${pageable.search || ''}`);
    //response co ca status ok hay ko header {} body
    //{page: 1, size: 10, content: []}
    //{ size: 15, content: [1,2,3]}
    //{page:1 , size: 15, content: [1,2,3]}
    const result = await response.json();
    pageable = {
        ...pageable,
        ...result
    };
    genderPagination();
    renderTBody(result.content);
    addEventEditAndDelete();
    //result tra ve data type map voi ben RestController
}

window.onload = async () => {
    await getList();
    onLoadSort();
}

const genderPagination = () => {
    ePagination.innerHTML = '';
    let str = '';
    //generate preview truoc
    str += `<li class="page-item ${pageable.first ? 'disabled' : ''}">
              <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
            </li>`
    //generate 1234

    for (let i = 1; i <= pageable.totalPages; i++) {
        str += ` <li class="page-item ${(pageable.page) === i ? 'active' : ''}" aria-current="page">
      <a class="page-link" href="#">${i}</a>
    </li>`
    }
    //
    //generate next truoc
    str += `<li class="page-item ${pageable.last ? 'disabled' : ''}">
              <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Next</a>
            </li>`
    //generate 1234
    ePagination.innerHTML = str;

    const ePages = ePagination.querySelectorAll('li'); // lấy hết li mà con của ePagination
    const ePrevious = ePages[0];
    const eNext = ePages[ePages.length-1]

    ePrevious.onclick = () => {
        if(pageable.page === 1){
            return;
        }
        pageable.page -= 1;
        getList();
    }
    eNext.onclick = () => {
        if(pageable.page === pageable.totalPages){
            return;
        }
        pageable.page += 1;
        getList();
    }
    for (let i = 1; i < ePages.length - 1; i++) {
        if(i === pageable.page){
            continue;
        }
        ePages[i].onclick = () => {
            pageable.page = i;
            getList();
        }
    }
}

const onLoadSort = () => {
    eHeaderPublishDate.onclick = () => {
        let sort = 'publishDate,desc'
        if(pageable.sortCustom?.includes('publishDate') &&  pageable.sortCustom?.includes('desc')){
            sort = 'publishDate,asc';
        }
        pageable.sortCustom = sort;
        getList();
    }
}
const onSearch = (e) => {
    e.preventDefault()
    pageable.search = eSearch.value;
    pageable.page = 1;
    getList();
}
const findById = async (id) => {
    const response = await fetch('/api/films/' + id);
    return await response.json();
}
const onShowEdit = async (id) => {
    filmSelected = await findById(id);
    $('#staticBackdropLabel').text('Edit Film');
    $('#staticBackdrop').modal('show');
    $('#name').val(filmSelected.name);
    $('#publishDate').val(filmSelected.publishDate);
    onChangeSelect2('#director', filmSelected.directorId);
    onChangeSelect2('#actors', filmSelected.actorsId);
    onChangeSelect2('#categories', filmSelected.categoriesId);
}

const addEventEditAndDelete = () => {
    const eEdits = tBody.querySelectorAll('.edit');
    const eDeletes = tBody.querySelectorAll('.delete');
    for (let i = 0; i < eEdits.length; i++) {
        console.log(eEdits[i].id)
        eEdits[i].addEventListener('click', () => {
            onShowEdit(eEdits[i].dataset.id);
        })
    }
}
const clearForm = () => {
    onChangeSelect2('#director', null);
    onChangeSelect2('#categories', null);
    onChangeSelect2('#actors', null);
}
const onShowCreate = () => {
    form.reset();
    filmSelected = {};
    clearForm();
    $('#staticBackdropLabel').text('Create Film');
}

document.getElementById('create').onclick = () => {
    onShowCreate();
}
async function createFilm() {
    const data = getData();

    return  await fetch('/api/films', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
}
const editFilm = async () => {
    let data = getData();
    return await fetch('/api/films/'+filmSelected.id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })

}

form.onsubmit = async (e) => {
    e.preventDefault();
    let message = "Created";
    let res;
    if(filmSelected.id){
        res = await editFilm();
        message = "Updated";
    }else{
        res =  await createFilm();
    }
    $('#staticBackdrop').modal('hide');
    if(res.ok){
        toastr.success(message);
    }
    getList();
}

function getData(){
    const categories = $("#categories").select2('data').map(e => e.id);
    const actors = $('#actors').select2('data').map(e => e.id);

    let data = getDataFromForm(form);

    data = {
        ...data,
        director: {id: data.director},
        categories,
        actors,
        id: filmSelected.id
    }
    return data;
}

const searchInput = document.querySelector('#search');

searchInput.addEventListener('search', () => {
    onSearch(event)
});


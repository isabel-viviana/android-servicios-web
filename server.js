const http = require('http');
const fs = require('fs');

const server = http.createServer((req, res) => {
    console.log(`[Servidor] Recibida petición: ${req.method} ${req.url}`);
    res.setHeader('Content-Type', 'application/json');

    let db = { libros: [] };
    try {
        if (fs.existsSync('./db.json')) {
            db = JSON.parse(fs.readFileSync('./db.json', 'utf8'));
        }
    } catch (e) {
        console.error("Error leyendo db.json", e);
    }

    const urlParts = req.url.split('?')[0].split('/');
    const resource = urlParts[1];
    const id = urlParts[2];

    if (resource === 'libros') {
        if (req.method === 'GET') {
            res.writeHead(200);
            res.end(JSON.stringify(db.libros));
        } else if (req.method === 'POST') {
            let body = '';
            req.on('data', chunk => body += chunk);
            req.on('end', () => {
                try {
                    const nuevoLibro = JSON.parse(body);
                    nuevoLibro.id = String(Date.now());
                    db.libros.push(nuevoLibro);
                    fs.writeFileSync('./db.json', JSON.stringify(db, null, 2));
                    res.writeHead(201);
                    res.end(JSON.stringify(nuevoLibro));
                } catch (e) {
                    res.writeHead(400);
                    res.end(JSON.stringify({ error: e.message }));
                }
            });
        } else if (req.method === 'PUT' || req.method === 'PATCH') {
            let body = '';
            req.on('data', chunk => body += chunk);
            req.on('end', () => {
                try {
                    const updates = JSON.parse(body);
                    const targetId = id || "1";
                    let index = db.libros.findIndex(l => String(l.id) === String(targetId));
                    if (index !== -1) {
                        db.libros[index] = { ...db.libros[index], ...updates };
                    } else {
                        db.libros.push({ id: targetId, ...updates });
                    }
                    fs.writeFileSync('./db.json', JSON.stringify(db, null, 2));
                    res.writeHead(200);
                    res.end(JSON.stringify(db.libros[index] || updates));
                } catch (e) {
                    res.writeHead(400);
                    res.end(JSON.stringify({ error: e.message }));
                }
            });
        } else if (req.method === 'DELETE') {
            const targetId = id || "1";
            db.libros = db.libros.filter(l => String(l.id) !== String(targetId));
            fs.writeFileSync('./db.json', JSON.stringify(db, null, 2));
            res.writeHead(200);
            res.end(JSON.stringify({ success: true, message: "Libro eliminado" }));
        } else {
            res.writeHead(405);
            res.end(JSON.stringify({ error: "Method not allowed" }));
        }
    } else {
        res.writeHead(404);
        res.end(JSON.stringify({ error: "Not found" }));
    }
});

server.listen(3000, '0.0.0.0', () => {
    console.log('Servidor de prueba Node.js corriendo en http://localhost:3000');
});

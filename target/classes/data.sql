-- Insertar datos iniciales de productos (solo si no existen)
-- Nota: Se usa MERGE para evitar duplicados
MERGE INTO products (id, name, price, ingredients, description, stock_quantity) KEY(name) VALUES
(1, 'Zumo de Naranja', 5.1, 'Naranja', 'Zumo natural de naranja recién exprimido', 15),
(2, 'Zumo de Limón', 5.1, 'Limón', 'Zumo fresco de limón con hielo', 12),
(3, 'Zumo de Pomelo', 5.1, 'Pomelo', 'Zumo de pomelo rosado natural', 10),
(4, 'Zumo de Piña', 5.1, 'Piña recién cortada', 'Zumo tropical de piña recién cortada', 14),
(5, 'Zumo de Manzana', 5.1, 'Manzana,Zumo de naranja', 'Zumo de manzana y naranja', 16),
(6, 'Zumo de Melocotón', 5.1, 'Melocotón,Zumo de naranja', 'Zumo de melocotón con zumo de naranja', 11),
(7, 'Zumo de Melón', 5.1, 'Melón recién cortado', 'Zumo refrescante de melón recién cortado', 9),
(8, 'Zumo de Sandía', 5.1, 'Sandía recién cortada', 'Zumo de sandía recién cortada', 13),
(9, 'Zumo de Zanahoria', 5.1, 'Zanahoria,Zumo de naranja', 'Zumo de zanahoria con zumo de naranja', 10),
(10, 'Zumo de Kiwi', 5.1, 'Kiwi,Naranja', 'Zumo de kiwi y naranja', 12),
(11, 'Zumo de Frambuesa', 6.8, 'Frambuesa,Zumo de naranja', 'Zumo de frambuesa con zumo de naranja', 8),
(12, 'Zumo de Moras', 6.8, 'Mora,Zumo de naranja', 'Zumo de mora con zumo de naranja', 7),
(13, 'Batido de Fresones', 5.1, 'Fresones,Leche', 'Batido cremoso de fresones con leche', 14),
(14, 'Batido de Plátano', 5.1, 'Plátano,Leche', 'Batido suave de plátano con leche', 15),
(15, 'Batido de Yogurt', 5.1, 'Yogurt en polvo,Leche', 'Batido de yogurt en polvo con leche', 10),
(16, 'Batido de Coco', 6.5, 'Helado de coco,Leche', 'Batido tropical de helado de coco con leche', 11),
(17, 'Batido de Chocolate Natural', 5.1, 'Chocolate en polvo,Leche', 'Batido de chocolate natural en polvo con leche', 16),
(18, 'Batido de Chocolate Fondant', 6.5, 'Helado de chocolate fondant,Leche', 'Batido de helado de chocolate fondant con leche', 9),
(19, 'Batido de Chocolate Blanco', 6.5, 'Helado de chocolate blanco,Chocoflakes,Leche', 'Batido de chocolate blanco con chocoflakes y leche', 8),
(20, 'Batido de Vainilla', 6.5, 'Helado de vainilla,Leche', 'Batido suave de helado de vainilla con leche', 13);

-- Insertar datos iniciales de usuarios (solo si no existen)
-- Nota: Se usa MERGE para evitar duplicados
MERGE INTO users (id, name, email, password, role) KEY(email) VALUES
(1, 'Admin Usuario', 'admin@frutolandia.com', 'admin123', 'ADMIN'),
(2, 'Usuario Cliente', 'cliente@frutolandia.com', 'user123', 'USER');

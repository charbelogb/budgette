import { Link } from 'react-router-dom'

export default function LandingPage() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-amber-50 via-white to-amber-50">
      {/* Navigation */}
      <nav className="flex items-center justify-between px-6 py-4 max-w-6xl mx-auto">
        <div className="flex items-center gap-2">
          <span className="text-2xl">💰</span>
          <span className="text-xl font-bold text-gray-900">Budgette</span>
        </div>
        <div className="flex items-center gap-4">
          <Link to="/login" className="text-sm text-gray-600 hover:text-gray-900">
            Connexion
          </Link>
          <Link
            to="/register"
            className="px-4 py-2 bg-amber-500 text-white text-sm font-medium rounded-lg hover:bg-amber-600 transition-colors"
          >
            Commencer gratuitement
          </Link>
        </div>
      </nav>

      {/* Hero */}
      <section className="max-w-6xl mx-auto px-6 pt-20 pb-24 text-center">
        <div className="inline-flex items-center gap-2 px-3 py-1 bg-amber-100 text-amber-700 rounded-full text-sm font-medium mb-6">
          🇧🇯 Conçu pour le Bénin
        </div>
        <h1 className="text-5xl font-bold text-gray-900 mb-6">
          Gérez votre argent Mobile Money{' '}
          <span className="text-amber-500">en un seul endroit</span>
        </h1>
        <p className="text-xl text-gray-600 mb-10 max-w-2xl mx-auto">
          Budgette consolide vos comptes MTN Mobile Money et Moov Money pour vous
          donner une vue complète de vos finances en FCFA.
        </p>
        <div className="flex items-center justify-center gap-4">
          <Link
            to="/register"
            className="px-6 py-3 bg-amber-500 text-white font-medium rounded-xl hover:bg-amber-600 transition-colors shadow-lg shadow-amber-200"
          >
            Créer mon compte
          </Link>
          <Link
            to="/login"
            className="px-6 py-3 bg-white text-gray-700 font-medium rounded-xl border border-gray-200 hover:bg-gray-50 transition-colors"
          >
            Se connecter
          </Link>
        </div>
      </section>

      {/* Features */}
      <section className="max-w-6xl mx-auto px-6 pb-24">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {[
            {
              icon: '📱',
              title: 'MTN & Moov Money',
              description: 'Connectez vos comptes MTN Mobile Money et Moov Money en quelques secondes.',
            },
            {
              icon: '📊',
              title: 'Tableau de bord unifié',
              description: 'Visualisez tous vos soldes et transactions sur un seul écran.',
            },
            {
              icon: '🔄',
              title: 'Synchronisation auto',
              description: 'Vos transactions se synchronisent automatiquement en temps réel.',
            },
          ].map((feature) => (
            <div key={feature.title} className="bg-white p-6 rounded-xl border border-gray-200">
              <span className="text-3xl mb-3 block">{feature.icon}</span>
              <h3 className="font-semibold text-gray-900 mb-2">{feature.title}</h3>
              <p className="text-sm text-gray-600">{feature.description}</p>
            </div>
          ))}
        </div>
      </section>

      {/* Footer */}
      <footer className="border-t border-gray-200 py-6 text-center text-sm text-gray-400">
        © 2024 Budgette · Finances personnelles Mobile Money · Bénin 🇧🇯
      </footer>
    </div>
  )
}
